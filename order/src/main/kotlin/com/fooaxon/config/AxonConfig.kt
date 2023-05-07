package com.fooaxon.config

import com.fooaxon.order.aggregate.OrderAggregate
import org.axonframework.common.caching.Cache
import org.axonframework.common.caching.WeakReferenceCache
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventsourcing.*
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.modelling.command.Repository
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration::class)
class AxonConfig {

    @Bean
    fun orderAggregateFactory(): AggregateFactory<OrderAggregate> {
        return GenericAggregateFactory(OrderAggregate::class.java)
    }

    @Bean
    fun aggregateSnapshotter(
        eventStore: EventStore,
        transactionManager: TransactionManager,
        orderAggregateFactory: AggregateFactory<OrderAggregate>,
    ): Snapshotter {
        return AggregateSnapshotter
            .builder()
            .eventStore(eventStore)
            .aggregateFactories(orderAggregateFactory)
            .transactionManager(transactionManager)
            .build()
    }

    @Bean
    fun orderSnapshotTriggerDefinition(
        aggregateSnapshotter: Snapshotter,
    ): SnapshotTriggerDefinition {
        val snapshotThreshold = 5
        return EventCountSnapshotTriggerDefinition(aggregateSnapshotter, snapshotThreshold)
    }

    @Bean
    fun cache(): Cache {
        return WeakReferenceCache()
    }

    @Bean
    fun orderAggregateRepository(
        eventStore: EventStore,
        orderSnapshotTriggerDefinition: SnapshotTriggerDefinition,
        cache: Cache,
        orderAggregateFactory: AggregateFactory<OrderAggregate>,
    ): Repository<OrderAggregate> {
        return EventSourcingRepository
            .builder(OrderAggregate::class.java)
            .aggregateFactory(orderAggregateFactory)
            .eventStore(eventStore)
            .snapshotTriggerDefinition(orderSnapshotTriggerDefinition)
            .cache(cache) // axon snapshot Store에 저장할 때 예외가 발생한다면, local cache는 스냅샷이 저장될 수 있으나, axon store에는 스냅샷 저장안될 수도있음.
            .build<EventSourcingRepository<OrderAggregate>>()
    }
}

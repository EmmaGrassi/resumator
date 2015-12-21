package io.sytac.resumator.store.sql.mapper;

import io.sytac.resumator.model.Event;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.ClobTypeHandler;

import java.util.List;

/**
 * Maps SQL rows into {@link io.sytac.resumator.model.Event instances}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface EventMapper {

    @Insert("INSERT into resumator_events(event_id, stream_id, insert_order, stream_order, payload, created_at, event_type) VALUES (#{id}, #{streamId}, #{insertOrder}, #{streamOrder}, #{payload}, #{created}, #{type})")
    void put(Event event);

    @Results({
            @Result(property = "id", column = "event_id"),
            @Result(property = "streamId", column = "stream_id"),
            @Result(property = "insertOrder", column = "insert_order"),
            @Result(property = "streamOrder", column = "stream_order"),
            @Result(property = "payload", column="payload", typeHandler = ClobTypeHandler.class), // not working!
            @Result(property = "created", column = "created_at"),
            @Result(property = "type", column = "event_type")
    })
    @Select("SELECT * FROM resumator_events ORDER BY created_at ASC")
    List<Event>getAll();

    @Delete("DELETE FROM resumator_events")
    void removeAll();

    @Select("SELECT MAX(insert_order) FROM resumator_events")
    Long getLastInsertOrder();

    @Select("SELECT MAX(stream_order) FROM resumator_events WHERE stream_id=#{stream}")
    Long getLastStreamOrder(@Param("stream") String streamId);
}

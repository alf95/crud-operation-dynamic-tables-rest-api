package org.jooq.example.spring.dto;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "entity",
        "filters",
        "populate"
})
public class InfoTable {

    @JsonProperty("entity")
    private String entity;
    @JsonProperty("filters")
    private List<FilterQuery> filters;
    @JsonProperty(value = "populate")
    private List<String> populate = new ArrayList<>();

    public InfoTable() {
    }

    public InfoTable(String entity, List<FilterQuery> filters, List<String> populate) {
        this.entity = entity;
        this.filters = filters;
        this.populate = populate;
    }

    @JsonProperty("entity")
    public String getEntity() {
        return entity;
    }

    @JsonProperty("entity")
    public void setEntity(String entity) {
        this.entity = entity;
    }

    @JsonProperty("filters")
    public List<FilterQuery> getFilters() {
        return filters;
    }

    @JsonProperty("filters")
    public void setFilters(List<FilterQuery> filters) {
        this.filters = filters;
    }

    @JsonProperty("populate")
    public List<String> getPopulate() {
        return populate;
    }

    @JsonProperty("populate")
    public void setPopulate(List<String> populate) {
        this.populate = populate;
    }

}
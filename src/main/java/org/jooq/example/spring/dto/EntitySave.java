package org.jooq.example.spring.dto;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "entity",
        "fields"
})
@Generated("jsonschema2pojo")
public class EntitySave {

    @JsonProperty("entity")
    private String entity;
    @JsonProperty("fields")
    private Map<String,Object> fields = null;

    @JsonProperty("entity")
    public String getEntity() {
        return entity;
    }

    @JsonProperty("entity")
    public void setEntity(String entity) {
        this.entity = entity;
    }

    @JsonProperty("fields")
    public Map<String,Object> getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}


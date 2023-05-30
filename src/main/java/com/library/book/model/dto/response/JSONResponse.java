package com.library.book.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONResponse {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public JSONResponse() {
    }

    public JSONResponse(String message, Object error) {
        this.status = "error";
        this.message = message;
        this.error = error;
    }

    public JSONResponse(Object data) {
        this.status = "success";
        this.data = data;
    }
}

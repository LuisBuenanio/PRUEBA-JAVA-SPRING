package com.pruebatecnica.backend.Util;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonSchemaValidator {
    private final Schema schema;

    public JsonSchemaValidator() {
        JSONObject schemaJson = new JSONObject("""
       {
               "$schema": "http://json-schema.org/draft-07/schema#",
                          "type": "object",
                          "properties": {
                            "firstname": { "type": "string" },
                            "lastname": { "type": "string" },
                            "email": { "type": "string", "format": "email" },
                            "username": { "type": "string" },
                            "password": { "type": "string", "minLength": 8 },
                            "ci": { "type": "string", "pattern": "^[0-9]{10}$" }
                          },
                          "required": ["firstname", "lastname", "password", "ci"]
                        }
        """);
        this.schema = SchemaLoader.load(schemaJson);
    }

    public boolean validate(String json) {
        try {
            schema.validate(new JSONObject(json));
            return true; // JSON válido según el esquema
        } catch (Exception e) {
            return false; // JSON inválido según el esquema
        }
    }
}

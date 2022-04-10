package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.config;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String URI = "mongodb+srv://vakinha:1XfpGSVHBmFPxEKH@vakinha.jyblt.mongodb.net/?retryWrites=true&w=majority";
    private MongoClient client;

    public MongoDatabase connection() throws BusinessRuleException {
        MongoDatabase connection;
        try {
            client = MongoClients.create(URI);
            connection = client.getDatabase("vakinha_dashboard");
        } catch (MongoClientException e) {
            throw new BusinessRuleException(e.getMessage());
        }
        return connection;
    }

    public void close() {
        client.close();
    }

}

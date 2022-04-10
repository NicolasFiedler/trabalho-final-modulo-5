package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.config.MongoDBConnection;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.DonateEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.dashboard.DonateDashBoardEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;

@Repository
public class DonateDashBoardRepository {

    private final MongoDBConnection conn = new MongoDBConnection();

    private MongoCollection<Document> collection;

    private void connect() throws BusinessRuleException {
        collection = conn.connection().getCollection("vakinha");
    }

    private void close() {
        conn.close();
    }


    public List<DonateEntity> findAll() throws Exception {
        List<DonateEntity> list = new ArrayList<>();
        try {
            connect();

            FindIterable<Document> findIterable = this.collection.find();

            for (Document document : findIterable) {
                list.add(this.documentToDonate(document));
            }

//            while (cursor.hasNext()) {
//                final Document document = cursor.next();
//                list.add(this.documentToDonate(document));
//            }

            close();
            return list;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private DonateEntity documentToDonate(Document document) {
        DonateEntity donateEntity = new DonateEntity();
        donateEntity.setIdDonate(Integer.parseInt(document.get("id_vakinha").toString()));
        donateEntity.setIdRequest(Integer.parseInt(document.get("id_request").toString()));
        donateEntity.setDonatorName(document.get("donator_name").toString());
        donateEntity.setDonatorEmail(document.get("donator_email").toString());
        donateEntity.setDonateValue(Double.parseDouble(document.get("donate_value").toString()));
        donateEntity.setDescription(document.get("donate_description").toString());

        return donateEntity;
    }

}

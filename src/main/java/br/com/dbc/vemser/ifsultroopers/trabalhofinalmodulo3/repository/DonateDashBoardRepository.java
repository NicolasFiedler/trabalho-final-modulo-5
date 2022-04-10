package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.config.MongoDBConnection;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.DonateEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;

@Repository
public class DonateDashBoardRepository {

    private final MongoDBConnection conn = new MongoDBConnection();

    private MongoCollection<Document> collection;

    private void connect() throws BusinessRuleException {
        collection = conn.connection().getCollection("donates");
    }

    private void close() {
        conn.close();
    }


    public List<DonateEntity> findAll() throws Exception {
        List<DonateEntity> list = new ArrayList<>();
        try {
            connect();

            FindIterable<Document> findIterable = collection.find();

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

    public void insert(DonateEntity donateEntity) throws BusinessRuleException {
        try {
            connect();

            Document document = new Document("id_donate", donateEntity.getIdDonate())
                    .append("id_request", donateEntity.getIdRequest())
                    .append("donator_name", donateEntity.getDonatorName())
                    .append("donator_email", donateEntity.getDonatorEmail())
                    .append("donate_value", donateEntity.getDonateValue())
                    .append("donate_description", donateEntity.getDescription());

            collection.insertOne(document);
            close();

        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    public void update(Integer id, DonateEntity donateEntity) throws Exception {
        try {
            connect();

            Bson update = Updates.combine(
                    Updates.set("id_request", donateEntity.getIdRequest()),
                    Updates.set("donator_name", donateEntity.getDonatorName()),
                    Updates.set("donator_email", donateEntity.getDonatorEmail()),
                    Updates.set("donate_value", donateEntity.getDonateValue()),
                    Updates.set("donate_description", donateEntity.getDescription())
            );

            Document query = new Document("id_donate", id);
            UpdateOptions options = new UpdateOptions().upsert(true);

            collection.updateOne(query, update, options);

            close();

            donateEntity.setIdDonate(id);
        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    public void deleteById(Integer id) throws Exception {
        try {
            connect();

            Document query = new Document("id_donate", id);

            collection.deleteOne(query);

            close();
        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    private DonateEntity documentToDonate(Document document) {
        DonateEntity donateEntity = new DonateEntity();
        donateEntity.setIdDonate(Integer.parseInt(document.get("id_donate").toString()));
        donateEntity.setIdRequest(Integer.parseInt(document.get("id_request").toString()));
        donateEntity.setDonatorName(document.get("donator_name").toString());
        donateEntity.setDonatorEmail(document.get("donator_email").toString());
        donateEntity.setDonateValue(Double.parseDouble(document.get("donate_value").toString()));
        donateEntity.setDescription(document.get("donate_description").toString());

        return donateEntity;
    }

}

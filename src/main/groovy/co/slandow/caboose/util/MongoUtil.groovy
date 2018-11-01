package co.slandow.caboose.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Query

class MongoUtil {

    private static final ObjectMapper om = new ObjectMapper()

    static Query buildQuery(Map query){
        return new BasicQuery(om.valueToTree(query).toString())
    }

    static Query buildPagedQuery(Map query = null, int page, int size) {
        final q = query ? buildQuery(query) : new Query()
        return q.with(new PageRequest(page, size))
    }
}

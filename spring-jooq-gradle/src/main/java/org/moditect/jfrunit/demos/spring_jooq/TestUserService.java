package org.moditect.jfrunit.demos.spring_jooq;

import org.jooq.DSLContext;
import org.moditect.jfrunit.demos.spring_jooq.generated.tables.TestUser;
import org.moditect.jfrunit.demos.spring_jooq.generated.tables.records.TestUserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserService {

    private final DSLContext dsl;

    @Autowired
    public TestUserService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public boolean createUser(String username, int age) {
        int numInserted = dsl.insertInto(TestUser.TEST_USER)
                .set(TestUser.TEST_USER.USERNAME, username)
                .set(TestUser.TEST_USER.AGE, age)
                .execute();
        return numInserted == 1;
    }

    public TestUserRecord getUserByUsername(String username) {
        return dsl.selectFrom(TestUser.TEST_USER)
                .where(TestUser.TEST_USER.USERNAME.eq(username))
                .fetchOne();
    }

}

package org.moditect.jfrunit.demos.spring_jooq;

import org.jooq.ExecuteContext;

public class ExecuteContextSqlConverter {
    public static String convert(ExecuteContext ctx) {
        return ctx.sql();
    }
}

package com.keddok.test.util;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @author RMakhmutov
 * @since 31.03.2017
 */
public class BrokenTransactionManager extends DataSourceTransactionManager {
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        throw new RuntimeException("See the power of the BrokenTransactionManager in action!");
    }
}

package com.service.repository.builder;

import com.service.enums.Condition;
import com.service.enums.SortOrder;
import com.service.model.Pagination;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public abstract class BaseQueryBuilder {

    public void addToWhereClause(StringBuilder builder, String column, Object value,
        Condition condition) {
        if (Condition.EQ.equals(condition) && Objects.nonNull(value)) {
            checkAndAddClause(builder);
            builder.append(
                MessageFormat.format(" {0} {1} ''{2}'' ", column, condition.getValue(), value));
        } else if (Condition.IN.equals(condition) && Objects.nonNull(value)
            && !CollectionUtils.isEmpty(
            (List<String>) value)) {
            checkAndAddClause(builder);
            builder.append(
                MessageFormat.format(" {0} {1} (''{2}'') ", column, condition.getValue(),
                    String.join("','", (List<String>) value)));
        } else {
        }
    }

    public void addPagination(StringBuilder builder, Pagination pagination,
        String alias) {
        if (Objects.isNull(pagination)) {
            builder.append(
                MessageFormat.format(" ORDER BY {0}{1} {2} ", alias, "createdTime",
                    SortOrder.DESC.getValue()));
            builder.append(
                MessageFormat.format(" LIMIT {0} OFFSET {1} ", 10, 0));
        } else {
            builder.append(
                MessageFormat.format(" ORDER BY {0}{1} {2} ", alias, pagination.getSortBy(),
                    pagination.getOrder().getValue()));
            builder.append(
                MessageFormat.format(" LIMIT {0} OFFSET {1} ", pagination.getLimit().longValue(),
                    pagination.getOffSet().longValue()));
        }

    }

    private void checkAndAddClause(StringBuilder builder) {
        if (!builder.toString().contains(" WHERE ")) {
            builder.append(" WHERE ");
        } else {
            builder.append(" AND ");
        }
    }

    public abstract String createSearchCriteriaQuery();
}

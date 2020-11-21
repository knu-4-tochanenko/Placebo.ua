package ua.placebo.api.repository.impl;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.entity.DrugEntity;
import ua.placebo.api.entity.DrugEntity_;
import ua.placebo.api.repository.CustomDrugRepository;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional(readOnly = true)
public class DrugRepositoryImpl implements CustomDrugRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<DrugEntity> findDrugs(DrugFilter filter, Pageable pageable) {
    Session session = em.unwrap(Session.class);
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Object> query = builder.createQuery(Object.class);
    Root<DrugEntity> drug = query.from(DrugEntity.class);
    List<Predicate> predicates = new ArrayList<>();

    if (filter.getName() != null) {
      predicates.add(builder.like(builder.lower(drug.get(DrugEntity_.name)), buildLikePattern(filter.getName())));
    }

    if (filter.getType() != null) {
      predicates.add(builder.equal(drug.get(DrugEntity_.type), filter.getType()));
    }

    if (filter.getMaxPrice() != null) {
      predicates.add(builder.lessThanOrEqualTo(drug.get(DrugEntity_.price), filter.getMaxPrice()));
    }

    if (filter.getMinPrice() != null) {
      predicates.add(builder.greaterThanOrEqualTo(drug.get(DrugEntity_.price), filter.getMinPrice()));
    }

    Predicate resultPredicate = builder.and(predicates.toArray(new Predicate[0]));

    query.where(resultPredicate);
    query.select(builder.count(drug));
    Long totalRows = (Long) em.createQuery(query).getSingleResult();

    addOrder(builder, drug, query, pageable.getSort());
    query.select(drug);
    List<DrugEntity> results = em.createQuery(query)
        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList()
        .stream()
        .map(item -> (DrugEntity) item)
        .collect(toList());
    return new PageImpl<>(results, pageable, totalRows);
  }

  private void addOrder(CriteriaBuilder builder, Root<?> root, CriteriaQuery<Object> query, Sort sort) {
    List<Order> orders = !sort.isSorted() ? Collections.emptyList() : sort.stream()
        .map(order -> order.isAscending() ? builder.asc(root.get(order.getProperty())) : builder.desc(root.get(order.getProperty())))
        .collect(toList());

    if (!orders.isEmpty()) {
      query.orderBy(orders);
    }
  }

  private String buildLikePattern(String search) {
    return "%" + search.toLowerCase() + "%";
  }
}

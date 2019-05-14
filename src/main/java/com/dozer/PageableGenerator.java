package com.dozer;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 5/14/2019.
 */
public class PageableGenerator  {

    public static final String DEFAULT_PAGINATION_OFFSET = "0";
    public static final String DEFAULT_PAGINATION_LIMIT = "10";
    private static final String DESC_ORDER = "-";

    /**
     * Genera la información de la paginación, en base al criterio de
     * ordenamiento pasado, el offset y limit.
     *
     * @param compoundSort
     * @param offset
     * @param limit
     * @return
     */
    public static Pageable generatePageable(String compoundSort, int offset, int limit) {
        return PageableGenerator.generatePageable(generateSort(compoundSort), offset, limit);
    }

    /**
     * Genera la información de la paginación, en base al criterio de
     * ordenamiento pasado, el offset y limit.
     *
     * @param sort
     *            información de ordenación.
     * @param offset
     *            corrimiento del primer elemento de esta página en relación al
     *            primer elemento de la query.
     * @param limit
     *            límite de elementos por página.
     * @return información de paginación con los datos pasados como argumento.
     */
    public static Pageable generatePageable(Sort sort, int offset, int limit) {
        return new PageRequest(offset / limit, limit, sort);
    }

    /**
     * Genera los criterios de ordenamiento, siguiendo la nomenclatura estandar:
     * lista de atributos separados por coma, con el prefijo + para ordenamiento
     * ascendente y - para ordenamiento descendente
     *
     * Ej: -manufactorer,+model
     *
     * @param compoundSort
     * @return
     */
    public static Sort generateSort(String compoundSort) {
        Sort sort = null;
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (null != compoundSort && !compoundSort.isEmpty()) {
            String[] stringOrders = compoundSort.split(",");
            for (String stringOrder : stringOrders) {
                orders.add(generateOrder(stringOrder));
            }
            sort = new Sort(orders);
        }
        return sort;
    }

    private static Sort.Order generateOrder(@Pattern(regexp = "[+-][a-z]*") String paramOrder) {
        Sort.Order order = new Sort.Order((paramOrder.substring(0, 1).equals(DESC_ORDER) ? Sort.Direction.DESC : Sort.Direction.ASC),
                paramOrder.substring(1, paramOrder.length()));
        return order;
    }

}

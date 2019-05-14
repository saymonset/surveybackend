package com.dozer;

/**
 * Created by simon on 5/14/2019.
 */

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper para conversión de objetos a DTOs y viceversa.
 */
public class DozerHelper {

    /**
     * Convierte los elementos de la lista "source" al tipo "destinationClass"
     * usando el dozer mapper.
     *
     * @param mapper
     *            dozer mapper a usar para la conversión.
     * @param source
     *            lista de elementos a convertir.
     * @param destinationClass
     *            tipo al que los elementos se van a convertir.
     * @return lista con los elementos convertidos según se indica en los
     *         parámetros.
     */
    public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destinationClass) {
        if (null == source) {
            return null;
        }
        return source.stream().map(ob -> mapper.map(ob, destinationClass)).collect(Collectors.toList());
    }

    /**
     * Convierte los elementos de la lista "source" al tipo "destinationClass"
     * usando el dozer mapper.
     *
     * @param mapper
     *            dozer mapper a usar para la conversión.
     * @param source
     *            lista de elementos a convertir.
     * @param destinationClass
     *            tipo al que los elementos se van a convertir.
     * @param mapId
     *            id del mapeo de dozer a aplicar para la conversion de los
     *            objetos
     * @return lista con los elementos convertidos según se indica en los
     *         parámetros.
     */
    public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destinationClass,
                                     final String mapId) {
        if (null == source) {
            return null;
        }
        return source.stream().map(ob -> mapper.map(ob, destinationClass, mapId)).collect(Collectors.toList());
    }

    /**
     * Convierte los elementos del set "source" al tipo "destinationClass"
     * usando el dozer mapper.
     *
     * @param mapper
     *            dozer mapper a usar para la conversión.
     * @param source
     *            set de elementos a convertir.
     * @param destinationClass
     *            tipo al que los elementos se van a convertir.
     * @return set con los elementos convertidos según se indica en los
     *         parámetros.
     */
    public static <T, U> Set<U> map(final Mapper mapper, final Set<T> source, final Class<U> destinationClass) {
        if (null == source) {
            return null;
        }
        return source.stream().map(ob -> mapper.map(ob, destinationClass)).collect(Collectors.toSet());
    }

    /**
     * Convierte los elementos de la página "source" al tipo "destinationClass"
     * usando el dozer mapper.
     *
     * @param mapper
     *            dozer mapper a usar para la conversión.
     * @param source
     *            página de elementos a convertir.
     * @param destinationClass
     *            tipo al que los elementos se van a convertir.
     * @return lista con los elementos convertidos según se indica en los
     *         parámetros.
     */
    public static <T, U> Page<U> map(final Mapper mapper, final Page<T> source, final Class<U> destinationClass) {
        if (null == source) {
            return null;
        }
        Sort sort = source.getSort();
        int offset = (int) (source.getNumber() * source.getSize());
        int limit = (int) source.getSize();
        List<U> content = new ArrayList<U>();
        // Si hay elementos, mapeo cada uno al tipo de dato del destinationClass
        if (source.getSize() > 0) {
            content = source.getContent().stream().map(elem -> mapper.map(elem, destinationClass))
                    .collect(Collectors.toList());
        }
        Pageable pageable = PageableGenerator.generatePageable(sort, offset, limit);
        return new PageImpl<U>(content, pageable, source.getTotalElements());
    }

}

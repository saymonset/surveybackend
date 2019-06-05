package com.repository.mongo;

import com.model.mongo.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 6/4/2019.
 */
public interface CompanyRepository   extends CrudRepository<Company, String> {
    Company findByCode(String code);
}

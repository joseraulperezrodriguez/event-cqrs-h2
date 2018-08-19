package com.bussiness.events.data.repository.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bussiness.events.domain.query.ParcelStatus;

public interface IParcelStatusRepository extends CrudRepository<ParcelStatus, String>, PagingAndSortingRepository<ParcelStatus, String> {

}

package com.bussiness.events.data.repository.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bussiness.events.domain.query.Parcel;

public interface IParcelRepository extends CrudRepository<Parcel, String>, PagingAndSortingRepository<Parcel, String> {

}

package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital, String> {


}

package com.ppjt10.skifriend.repository;

import com.ppjt10.skifriend.entity.Carpool;
import com.ppjt10.skifriend.entity.SkiResort;
import com.ppjt10.skifriend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CarpoolRepository extends JpaRepository<Carpool, Long> {

    Page<Carpool> findAllBySkiResortResortNameAndCarpoolTypeContainingAndStartLocationContainingAndEndLocationContainingAndDateContainingAndMemberNumIsOrderByCreateAtDesc(
            String resortName,
            String carpoolType,
            String startLocation,
            String endLocation,
            String date,
            int memberNum,
            Pageable pageable
    );

    Page<Carpool> findAllBySkiResortResortNameAndCarpoolTypeContainingAndStartLocationContainingAndEndLocationContainingAndDateContainingAndMemberNumIsGreaterThanEqualOrderByCreateAtDesc(
            String resortName,
            String carpoolType,
            String startLocation,
            String endLocation,
            String date,
            int memberNum,
            Pageable pageable
    );


    List<Carpool> findAllByUser(User user);

    Page<Carpool> findAllBySkiResortOrderByCreateAtDesc(SkiResort skiResort, Pageable pageable);

    List<Carpool> findAllByDateAndTime(String date, String time);
}

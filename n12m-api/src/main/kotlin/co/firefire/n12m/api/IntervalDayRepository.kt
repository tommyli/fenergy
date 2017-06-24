// Tommy Li (tommy.li@firefire.co), 2017-06-24

package co.firefire.n12m.api

import org.springframework.data.repository.PagingAndSortingRepository

interface IntervalDayRepository : PagingAndSortingRepository<IntervalDay, Long> {
}

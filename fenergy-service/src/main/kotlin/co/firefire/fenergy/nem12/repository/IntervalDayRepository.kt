// Tommy Li (tommy.li@firefire.co), 2017-06-24

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.IntervalDay
import org.springframework.data.repository.PagingAndSortingRepository

interface IntervalDayRepository : PagingAndSortingRepository<IntervalDay, Long> {
}

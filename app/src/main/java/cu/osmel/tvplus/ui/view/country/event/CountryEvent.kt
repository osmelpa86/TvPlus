package cu.osmel.tvplus.ui.view.country.event

import cu.osmel.tvplus.domain.model.Country

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
sealed class CountryEvent {
    data class InsertCountry(val country: Country) : CountryEvent()
    data class InsertAllCountry(val list: List<Country>) : CountryEvent()
    data class DeleteCountry(val country: Country) : CountryEvent()
    data class UpdateEvent(val country: Country) : CountryEvent()
}


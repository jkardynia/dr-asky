package com.jkgroup.drasky.commuting.intent

import com.jkgroup.drasky.commuting.bus.BusCheckingService
import com.jkgroup.drasky.commuting.bus.BusInfo
import com.jkgroup.drasky.commuting.repository.BusLocationsRepository
import com.jkgroup.drasky.commuting.repository.IntentFindBus
import com.jkgroup.drasky.intent.TemplateGenerator
import com.jkgroup.drasky.intent.repository.Location
import com.jkgroup.drasky.intent.repository.Profile
import com.jkgroup.drasky.intent.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

import static com.jkgroup.drasky.fixtures.ProfileFactory.createProfileWithHomeLocation
import static com.jkgroup.drasky.fixtures.ProfileFactory.createSimpleLocation

import static com.jkgroup.drasky.fixtures.IntentRequestFactory.*

@SpringBootTest
@ActiveProfiles("test")
class FindBusActionTests extends Specification{

    @Autowired private BusLocationsRepository busLocationsRepository
    @Autowired private ProfileRepository profileRepository
    @Autowired private TemplateGenerator templateGenerator

    private BusCheckingService busCheckingService

    def setup(){
        busCheckingService = Mock(BusCheckingService)
    }

    def "should find a bus when date and time parameters specified"(){
        given:
        def profileName = "default"
        def action = new FindBusAction(busLocationsRepository, profileRepository, busCheckingService, templateGenerator, profileName)
        def request = requestWithParams(['destination': 'home',
                                         'date'       : '2018-10-10T00:00:00Z',
                                         'time'       : '2018-10-10T13:30:20Z'])

        def location1 = createSimpleLocation('rynek', 'Cracow', 'Rynek', '1')
        def location2 = createSimpleLocation('home', 'Cracow', 'Sienkiewicza', '2')
        def profile = createProfileWithHomeLocation(profileName, location1)

        saveAsProfileLocation(profile, location2)
        profileRepository.save(profile)

        when:
        def result = action.execute(request)

        then:
        1* busCheckingService.findBus(location1.getAddress(),
                location2.getAddress(),
                LocalDateTime.of(2018, 10, 10, 13, 30, 20)) >> new BusInfo("144", LocalDateTime.of(2018,10,10,14,0))

        result.fulfillmentText.contains("144")
        result.fulfillmentText.contains(location2.getName())
        result.fulfillmentText.contains('2018-10-10')
        result.fulfillmentText.contains('2:00 PM')
    }

    private void saveAsProfileLocation(Profile profile, Location location){
        def intentFindBus = new IntentFindBus()
        intentFindBus.getLocations().add(location)
        intentFindBus.setProfile(profile)

        busLocationsRepository.save(intentFindBus)
    }
}

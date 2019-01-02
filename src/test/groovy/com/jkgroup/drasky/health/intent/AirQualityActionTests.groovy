package com.jkgroup.drasky.health.intent

import com.jkgroup.drasky.ClearDbTrait
import com.jkgroup.drasky.health.repository.AirQualityLocationsRepository
import com.jkgroup.drasky.health.repository.IntentAirQuality
import com.jkgroup.drasky.health.service.airly.AirQuality
import com.jkgroup.drasky.health.service.airly.AirlyService
import com.jkgroup.drasky.health.service.airly.Index
import com.jkgroup.drasky.health.service.airly.Measurement
import com.jkgroup.drasky.intent.TemplateGenerator
import com.jkgroup.drasky.intent.repository.Location
import com.jkgroup.drasky.intent.repository.Profile
import com.jkgroup.drasky.intent.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.sql.DataSource

import static com.jkgroup.drasky.fixtures.IntentRequestFactory.requestWithParams
import static com.jkgroup.drasky.fixtures.ProfileFactory.createProfileWithHomeLocation
import static com.jkgroup.drasky.fixtures.ProfileFactory.createSimpleLocation

@SpringBootTest
@ActiveProfiles("test")
class AirQualityActionTests extends Specification implements ClearDbTrait{

    @Autowired private AirQualityLocationsRepository airQualityLocationsRepository
    @Autowired private ProfileRepository profileRepository
    @Autowired private TemplateGenerator templateGenerator
    @Autowired private DataSource dataSource

    private AirlyService airlyService

    def setup(){
        airlyService = Mock(AirlyService)
        cleanDatabase(dataSource)
    }

    def "should check air quality in specified location"(){
        given:
        def profileName = "default"
        def adviceText = "this is advice"
        def descriptionText = "this is description"
        def action = new AirQualityAction(airlyService, templateGenerator, profileRepository, airQualityLocationsRepository, profileName)
        def request = requestWithParams(['location': 'home'])

        def location1 = createSimpleLocation('rynek', 'Cracow', 'Rynek', '1')
        def location2 = createSimpleLocation('home', 'Cracow', 'Sienkiewicza', '2')
        def profile = createProfileWithHomeLocation(profileName, location1)

        saveAsProfileLocation(profile, location2)
        profileRepository.save(profile)

        when:
        def result = action.execute(request)

        then:
        1* airlyService.checkAirQuality(location2.getLat(), location2.getLng()) >> new AirQuality(new Measurement([new Index(advice: adviceText, description: descriptionText)]))

        result.fulfillmentText.contains(location2.getName())
        result.fulfillmentText.contains(adviceText)
        result.fulfillmentText.contains(descriptionText)
    }

    def "should check air quality in home location"(){
        given:
        def profileName = "default"
        def adviceText = "this is advice"
        def descriptionText = "this is description"
        def action = new AirQualityAction(airlyService, templateGenerator, profileRepository, airQualityLocationsRepository, profileName)
        def request = requestWithParams(['location': 'unknown'])

        def homeLocation = createSimpleLocation('rynek', 'Cracow', 'Rynek', '1')
        def profile = createProfileWithHomeLocation(profileName, homeLocation)

        profileRepository.save(profile)

        when:
        def result = action.execute(request)

        then:
        1* airlyService.checkAirQuality(homeLocation.getLat(), homeLocation.getLng()) >> new AirQuality(new Measurement([new Index(advice: adviceText, description: descriptionText)]))

        result.fulfillmentText.contains(homeLocation.getName())
        result.fulfillmentText.contains(adviceText)
        result.fulfillmentText.contains(descriptionText)
    }

    private void saveAsProfileLocation(Profile profile, Location location){
        def intentFindBus = new IntentAirQuality()
        intentFindBus.getLocations().add(location)
        intentFindBus.setProfile(profile)

        airQualityLocationsRepository.save(intentFindBus)
    }
}

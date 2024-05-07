package learn.goodgames.domain;

import learn.goodgames.data.LocationRepository;
import learn.goodgames.models.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> findAll() { return repository.findAll(); }

    public Location findById(int locationId) { return repository.findById(locationId); }

    public Result<Location> add(Location location) {
        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getLocationId() != 0) {
            result.addMessage("locationId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        location = repository.add(location);
        result.setPayload(location);
        return result;
    }

    public Result<Location> update(Location location) {
        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getLocationId() <= 0) {
            result.addMessage("locationId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(location)) {
            String msg = String.format("locationId: %s, not found", location.getLocationId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Location> deleteById(int locationId) {
        Result<Location> result = new Result<>();

        // validate the location is not in use
        int usageCount = repository.getUsageCount(locationId);
        if (usageCount > 0) {
            String msg = String.format("locationId: %s, is in use", locationId);
            result.addMessage(msg, ResultType.INVALID);
            return result;
        }

        if (!repository.deleteById(locationId)) {
            String msg = String.format("locationId: %s, not found", locationId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Location> validate(Location location) {
        Result<Location> result = new Result<>();
        List<Location> all = repository.findAll();

        for(Location l : all) {
            if (l.equals(location)) {
                result.addMessage("duplicate locations with same name and address are not allowed", ResultType.INVALID);
                return result;
            }
        }

        if (location == null) {
            result.addMessage("location cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(location.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(location.getAddress())) {
            result.addMessage("address is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(location.getCity())) {
            result.addMessage("city is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(location.getState())) {
            result.addMessage("state is required", ResultType.INVALID);
        }

        if(location.getState() == null || location.getState().length() != 2 ) {
            result.addMessage("state must be a 2-letter abbreviation (ex. Florida = FL)", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(location.getPostalCode())) {
            result.addMessage("postalCode is required", ResultType.INVALID);
        }

        return result;
    }
}

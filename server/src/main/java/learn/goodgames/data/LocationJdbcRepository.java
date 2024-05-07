package learn.goodgames.data;

import learn.goodgames.models.Location;

import java.util.List;

public class LocationJdbcRepository implements LocationRepository{

    @Override
    public List<Location> findAll() {
        return null;
    }

    @Override
    public Location findById(int locationId) {
        return null;
    }

    @Override
    public Location add(Location location) {
        return null;
    }

    @Override
    public boolean update(Location location) {
        return false;
    }

    @Override
    public boolean deleteById(int locationId) {
        return false;
    }
}

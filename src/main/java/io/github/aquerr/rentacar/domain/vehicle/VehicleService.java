package io.github.aquerr.rentacar.domain.vehicle;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.vehicle.converter.VehicleConverter;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchParams;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchResult;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import io.github.aquerr.rentacar.repository.ReservationRepository;
import io.github.aquerr.rentacar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public VehicleFullData getVehicleFullData(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(this.vehicleConverter::toFullData)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public AvailableVehiclesSearchResult getVehiclesAvailable(AvailableVehiclesSearchParams params) {
        List<Integer> vehiclesIds = new ArrayList<>(vehicleRepository.findAllIds());
        List<Integer> notAvailableVehicleIds = reservationRepository.findAllNotAvailableVehiclesBetweenDates(params.getFrom(), params.getTo());
        vehiclesIds.removeAll(notAvailableVehicleIds);

        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());

        Page<VehicleEntity> page = vehicleRepository.findAllByIdIn(vehiclesIds, pageable);

        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        List<VehicleBasicData> vehicles = page.stream().map(this.vehicleConverter::toBasicData)
                .toList();
        return AvailableVehiclesSearchResult.of(vehicles, totalElements, totalPages);
    }

    public boolean isVehicleAvailable(int vehicleId, LocalDate dateFrom, LocalDate dateTo) {
        return reservationRepository.findReservationByVehicleIdAndDateBetween(vehicleId, dateFrom, dateTo).isEmpty();
    }

    public VehicleFullData saveVehicleWithImages(VehicleFullData vehicle, List<MultipartFile> images) {
        List<String> imageUris = new ArrayList<>();
        images.forEach(image -> imageUris.add(imageService.saveImage(image, ImageKind.VEHICLE).getUri().toString()));
        VehicleEntity vehicleEntity = vehicleConverter.fullDataToEntity(vehicle);
        vehicleEntity.setPhotoNames(imageUris.stream().map(imageService::retrieveImageName).toList());
        vehicleRepository.save(vehicleEntity);
        return vehicleConverter.toFullData(vehicleEntity);
    }
}

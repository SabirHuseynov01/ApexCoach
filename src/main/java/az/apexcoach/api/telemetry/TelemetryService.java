package az.apexcoach.api.telemetry;


import az.apexcoach.api.identity.UserEntity;
import az.apexcoach.api.identity.internal.UserRepository;

import az.apexcoach.api.simulation.NormalizedLap;
import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.internal.registry.TelemetryAdapterRegistry;
import az.apexcoach.api.telemetry.internal.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shared.error.NotFoundException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryFileRepository telemetryFileRepository;
    private final LapRepository lapRepository;
    private final UserRepository userRepository;
    private final TelemetryAdapterRegistry adapterRegistry;

    @Transactional
    public TelemetryUploadResponse upload(String email, SimulationType simulationType, MultipartFile file) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        byte[] content;

        try {
            content = file.getBytes();
        } catch (IOException e) {
            throw new NotFoundException("Unable to read uploaded file");
        }

        NormalizedTelemetry normalized = adapterRegistry
                .get(simulationType)
                .parse(content);

        TelemetryFileEntity telemetryFile = TelemetryFileEntity.builder()
                .user(user)
                .simulationType(simulationType)
                .fileName(file.getOriginalFilename())
                .fileSizeBytes(file.getSize())
                .uploadedAt(Instant.now())
                .build();

        telemetryFileRepository.save(telemetryFile);

        List<LapEntity> lapEntities = normalized.laps().stream()
                .map(lap -> toLapEntity(telemetryFile, lap))
                .toList();

        lapRepository.saveAll(lapEntities);

        return new TelemetryUploadResponse(
                telemetryFile.getId(),
                simulationType,
                telemetryFile.getFileName(),
                lapEntities.size(),
                telemetryFile.getUploadedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<LapDTO> getLaps(String email, UUID telemetryFileId) {
        TelemetryFileEntity telemetryFile = telemetryFileRepository.findById(telemetryFileId)
                .orElseThrow(() -> new NotFoundException("Telemetry file not found"));

        if (!telemetryFile.getUser().getEmail().equals(email)) {
            throw new NotFoundException("Telemetry file not found");
        }

        return lapRepository
                .findByTelemetryFileIdOrderByLapNumberAsc(telemetryFileId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private LapEntity toLapEntity(TelemetryFileEntity telemetryFile, NormalizedLap lap) {
        LapEntity lapEntity = LapEntity.builder()
                .telemetryFile(telemetryFile)
                .lapNumber(lap.lapNumber())
                .lapTime(lap.lapTime())
                .sector1Time(lap.sector1Time())
                .sector2Time(lap.sector2Time())
                .sector3Time(lap.sector3Time())
                .valid(lap.isValid())
                .build();

        List<TelemetryPointEntity> points = lap.points().stream()
                .map(point -> toPointEntity(lapEntity, point))
                .toList();

        lapEntity.getPoints().addAll(points);

        return lapEntity;
    }

    private TelemetryPointEntity toPointEntity(LapEntity lap, az.apexcoach.api.simulation.NormalizedTelemetryPoint p) {
        return TelemetryPointEntity.builder()
                .lap(lap)
                .sessionTime(p.sessionTime())
                .lapTime(p.lapTime())
                .lapDistance(p.lapDistance())
                .speed(p.speed())
                .rpm(p.rpm())
                .gear(p.gear())
                .throttle(p.throttle())
                .brake(p.brake())
                .clutch(p.clutch())
                .steeringAngle(p.steeringAngle())
                .lateralG(p.lateralG())
                .longitudinalG(p.longitudinalG())
                .verticalG(p.verticalG())
                .posX(p.posX())
                .posY(p.posY())
                .posZ(p.posZ())
                .yaw(p.yaw())
                .pitch(p.pitch())
                .roll(p.roll())
                .build();
    }

    private LapDTO toDto(LapEntity lap) {
        return new LapDTO(
                lap.getId(),
                lap.getLapNumber(),
                lap.getLapTime(),
                lap.getSector1Time(),
                lap.getSector2Time(),
                lap.getSector3Time(),
                lap.isValid()
        );
    }
}
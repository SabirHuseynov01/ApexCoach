package az.apexcoach.api.coaching;

import az.apexcoach.api.analysis.AnalysisService;
import az.apexcoach.api.analysis.DrivingMetricDTO;
import az.apexcoach.api.analysis.LapAnalysisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoachingService {

    private final AnalysisService analysisService;

    @Transactional(readOnly = true)
    public CoachingResponse coach(UUID lapId) {
        LapAnalysisDTO analysis = analysisService.getAnalysis(lapId);

        List<String> actionItems = new ArrayList<>();

        for (DrivingMetricDTO metric : analysis.metrics()) {
            if ("WARN".equalsIgnoreCase(metric.severity())) {
                actionItems.add(metric.message());
            }
        }

        if (actionItems.isEmpty()) {
            actionItems.add("Keep this driving style; no major warning detected in this lap.");
        }

        String summary = "Rule-based coaching completed for lap. "
                + actionItems.size()
                + " improvement area(s) detected.";

        return new CoachingResponse(lapId, summary, actionItems);
    }
}
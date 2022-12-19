package qraps.platform.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ReportDto {

    @Getter
    @Setter
    @Builder
    public static class Generate {

        private String targetName;

        private String reportId;

    }


    @Getter
    public static class Upload {

        private String targetName;

        private String reportId;

        private byte[] fileBytes;

        public Upload(ReportDto.Generate generateDto, byte[] file) {
            this.targetName = generateDto.getTargetName();
            this.reportId = generateDto.getReportId();
            this.fileBytes = file;
        }

        public String getFileName() {
            return targetName + "/" + reportId;
        }

    }


}

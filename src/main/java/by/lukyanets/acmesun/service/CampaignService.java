package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.campaign.CampaignDto;
import by.lukyanets.acmesun.dto.campaign.CampaignDtoAllInfo;
import by.lukyanets.acmesun.dto.campaign.CampaignDtoToList;
import by.lukyanets.acmesun.entity.CampaignEntity;
import by.lukyanets.acmesun.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CampaignService {
    CampaignEntity createNewCampaign(CampaignDto campaignDto, MultipartFile[] images) throws IOException;

    List<CampaignDtoToList> listOfAllCampaigns();

    List<CampaignDtoToList> listOfCampaignsBySubject(Subject subject);

    CampaignDtoAllInfo campaignInfoByName(String name);

    List<CampaignDtoAllInfo> campaignInfoByOwner(String name);

    void deleteCampaignByName(String name);

    boolean isCampaignHasBoughtBonuses(String name);
}

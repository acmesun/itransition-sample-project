package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.campaign.BonusDto;
import by.lukyanets.acmesun.dto.campaign.CampaignDto;
import by.lukyanets.acmesun.dto.campaign.CampaignDtoAllInfo;
import by.lukyanets.acmesun.dto.campaign.CampaignDtoToList;
import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.*;
import by.lukyanets.acmesun.repository.CampaignRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CampaignService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepo;
    private final UserRepository userRepo;
    private final CurrentUserService userService;
    private final Cloudinary cloudinary;

    @Override
    public CampaignEntity createNewCampaign(CampaignDto campaignDto, MultipartFile[] images) {
        if (campaignRepo.existsByCampaignName(campaignDto.getCampaignName())) {
            throw new EntityExistsException(campaignDto.getCampaignName() + " already exist.");
        }
        var campaignEntity = getDataFromDto(campaignDto);
        campaignEntity.setOwner(findOwner());
        campaignEntity.setImageList(Arrays.stream(images).map(this::processImage).collect(toList()));
        return campaignRepo.save(campaignEntity);
    }

    @Override
    public List<CampaignDtoToList> listOfAllCampaigns() {
        return campaignRepo.findAllByOrderByCampaignNameAsc()
                .stream()
                .map(entity -> new CampaignDtoToList(
                        entity.getId(),
                        entity.getCampaignName(),
                        entity.getSubject(),
                        entity.getTargetAmount(),
                        entity.getExpirationDate(),
                        entity.getOwner().getName())).collect(toList());
    }

    @Override
    public List<CampaignDtoToList> listOfCampaignsBySubject(Subject subject) {
        return campaignRepo.findAllBySubjectOrderByCampaignNameAsc(subject)
                .stream()
                .map(entity -> new CampaignDtoToList(
                        entity.getId(),
                        entity.getCampaignName(),
                        entity.getSubject(),
                        entity.getTargetAmount(),
                        entity.getExpirationDate(),
                        entity.getOwner().getName())).collect(toList());

    }

    @Override
    public List<CampaignDtoAllInfo> campaignInfoByOwner(String email) {
        var campaignEntitiesByOwner = campaignRepo.findAllByOwnerEmail(email);
        List<CampaignDtoAllInfo> campaignDtoAllInfos = new ArrayList<>();
        for (var campaignEntity : campaignEntitiesByOwner) {
            campaignDtoAllInfos.add(fromCampaignEntityToCampaignDtoAllInfo(campaignEntity));
        }
        return campaignDtoAllInfos;
    }

    @Override
    public void deleteCampaignByName(String name) {
        campaignRepo.delete(campaignRepo.findCampaignEntityByCampaignName(name));
    }

    @Override
    public boolean isCampaignHasBoughtBonuses(String name) {
        CampaignEntity campaignEntityByCampaignName = campaignRepo.findCampaignEntityByCampaignName(name);
        List<BonusEntity> bonusList = campaignEntityByCampaignName.getBonusList();
        int counter = 0;
        for (BonusEntity bonusEntity : bonusList) {
            Set<BuyBonusEntity> boughtBonuses = bonusEntity.getBoughtBonuses();
            if (boughtBonuses.size() >= 1) {
                counter++;
            }
        }
        return counter >= 1;
    }


    private CampaignDtoAllInfo fromCampaignEntityToCampaignDtoAllInfo(CampaignEntity campaignEntity) {
        var campaignDtoAllInfo = new CampaignDtoAllInfo();
        campaignDtoAllInfo.setId(campaignEntity.getId());
        campaignDtoAllInfo.setCampaignName(campaignEntity.getCampaignName());
        campaignDtoAllInfo.setCampaignDescription(campaignEntity.getCampaignDescription());
        campaignDtoAllInfo.setSubject(campaignEntity.getSubject());
        var bonusList = campaignEntity.getBonusList();
        List<BonusDto> bonusDtos = new ArrayList<>(bonusList.size());
        Integer currentAmount = 0;
        for (BonusEntity bonusEntity : bonusList) {
            BonusDto bonusDto = new BonusDto();
            bonusDto.setBonusName(bonusEntity.getBonusName());
            bonusDto.setAmount(bonusEntity.getAmount());
            bonusDto.setDescription(bonusEntity.getDescription());
            Set<BuyBonusEntity> boughtBonuses = bonusEntity.getBoughtBonuses();
            for (BuyBonusEntity buyBonusEntity : boughtBonuses) {
                Integer quantity = buyBonusEntity.getQuantity();
                Integer amount = buyBonusEntity.getBonus().getAmount();
                Integer sum = quantity * amount;
                currentAmount = currentAmount + sum;
            }
            bonusDtos.add(bonusDto);
        }
        campaignDtoAllInfo.setCurrentAmount(currentAmount);
        campaignDtoAllInfo.setBonusList(bonusDtos);
        campaignDtoAllInfo.setTargetAmount(campaignEntity.getTargetAmount());
        campaignDtoAllInfo.setExpirationDate(campaignEntity.getExpirationDate());
        campaignDtoAllInfo.setOwner(campaignEntity.getOwner().getEmail());
        var imageList = campaignEntity.getImageList();
        List<ImageDto> imageDtos = new ArrayList<>(imageList.size());
        for (var imageEntity : imageList) {
            ImageDto imageDto = new ImageDto();
            imageDto.setTitle(imageEntity.getTitle());
            imageDto.setUrl(imageEntity.getUrl());
            imageDtos.add(imageDto);
        }
        campaignDtoAllInfo.setImageList(imageDtos);
        return campaignDtoAllInfo;
    }

    @Override
    public CampaignDtoAllInfo campaignInfoByName(String name) {
        var campaignEntityByName = campaignRepo.findCampaignEntityByCampaignName(name);
        return fromCampaignEntityToCampaignDtoAllInfo(campaignEntityByName);
    }

    private UserEntity findOwner() {
        return userRepo.findUserEntityByEmail(userService.getCurrentUser().getEmail())
                .orElseThrow();
    }

    @SneakyThrows
    private ImageEntity processImage(MultipartFile image) {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), new HashMap<Long, Object>());
        return new ImageEntity(
                image.getOriginalFilename(),
                (String) uploadResult.get("url")
        );
    }

    private CampaignEntity getDataFromDto(CampaignDto campaignDto) {
        var campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName(campaignDto.getCampaignName());
        campaignEntity.setSubject(campaignDto.getSubject());
        campaignEntity.setCampaignDescription(campaignDto.getCampaignDescription());
        campaignEntity.setTargetAmount(campaignDto.getTargetAmount());
        campaignEntity.setExpirationDate(campaignDto.getExpirationDate());
        campaignEntity.setBonusList(campaignDto.getBonusList().stream().map(this::getDataFromDto).collect(toList()));
        return campaignEntity;
    }

    private BonusEntity getDataFromDto(BonusDto bonusDto) {
        var bonusEntity = new BonusEntity();
        bonusEntity.setBonusName(bonusDto.getBonusName());
        bonusEntity.setDescription(bonusDto.getDescription());
        bonusEntity.setAmount(bonusDto.getAmount());
        return bonusEntity;
    }

}

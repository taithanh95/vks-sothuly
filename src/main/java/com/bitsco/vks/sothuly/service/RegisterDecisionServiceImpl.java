package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.entities.RegisterDecision;
import com.bitsco.vks.sothuly.entities.RegisterDecisionNum;
import com.bitsco.vks.sothuly.model.Accused;
import com.bitsco.vks.sothuly.model.Case;
import com.bitsco.vks.sothuly.model.Decision;
import com.bitsco.vks.sothuly.model.Spp;
import com.bitsco.vks.sothuly.repository.RegisterDecisionDAO;
import com.bitsco.vks.sothuly.repository.RegisterDecisionNumRepository;
import com.bitsco.vks.sothuly.repository.RegisterDecisionRepository;
import com.bitsco.vks.sothuly.request.RegisterDecisionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RegisterDecisionServiceImpl implements RegisterDecisionService {
    @Autowired
    RegisterDecisionRepository registerDecisionRepository;

    @Autowired
    RegisterDecisionNumRepository registerDecisionNumRepository;

    @Autowired
    RegisterDecisionDAO registerDecisionDAO;

    @Autowired
    ManageService manageService;

    @Autowired
    CacheService cacheService;

    private RegisterDecision save(RegisterDecision registerDecision) throws Exception {
        if (registerDecision.getId() == null) registerDecision.setCreatedBy(cacheService.getUsernameFromHeader());
        else registerDecision.setUpdatedBy(cacheService.getUsernameFromHeader());
        return registerDecisionRepository.save(registerDecision);
    }

    private RegisterDecisionNum save(RegisterDecisionNum registerDecisionNum) throws Exception {
        if (registerDecisionNum.getId() == null) {
            registerDecisionNum.setCreatedBy(cacheService.getUsernameFromHeader());
        } else {
            registerDecisionNum.setUpdatedBy(cacheService.getUsernameFromHeader());
            registerDecisionNum.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        }
        return registerDecisionNumRepository.save(registerDecisionNum);
    }

    @Override
    public RegisterDecision createRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception {
        //Validate null
        ValidateCommon.validateNullObject(registerDecision.getType(), "type");
        ValidateCommon.validateNullObject(registerDecision.getStage(), "Giai ??o???n");
        ValidateCommon.validateNullObject(registerDecision.getSppCode(), "????n v??? ra Q??");
        ValidateCommon.validateNullObject(registerDecision.getDecisionCode(), "T??n quy???t ?????nh");
        ValidateCommon.validateNullObject(registerDecision.getIssuesDate(), "Ng??y c???p s???");
//        ValidateCommon.validateNullObject(registerDecision.getDecisionNum(), "S??? Q?? th???c th???");
        ValidateCommon.validateNullObject(registerDecision.getDecisionNumAuto(), "S??? Q??");
        if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.CASE)) {
            ValidateCommon.validateNullObject(registerDecision.getCaseCode(), "M?? v??? ??n");
        } else if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.ACCUSED)) {
            ValidateCommon.validateNullObject(registerDecision.getCaseCode(), "M?? v??? ??n");
            ValidateCommon.validateNullObject(registerDecision.getAccusedCode(), "M?? b??? can");
        } else if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)) {
            ValidateCommon.validateNullObject(registerDecision.getDenouncementId(), "M?? tin b??o");
        }

        // Ki???m tra SPP c?? ph???i ph??ng c???a t???nh ho???c c???a c???p cao kh??ng?
        Spp spp = cacheService.getSppFromCache(registerDecision.getSppCode());
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t??m th???y th??ng tin vi???n ki???m s??t trong Cache");
        // N???u ????ng th?? l???y sppId c???a c???p cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            registerDecision.setSppCode(spp.getSppParent());

        if (StringCommon.isNullOrBlank(registerDecision.getSppCode()))
            throw new CommonException(Response.DATA_NOT_FOUND, "????n v??? vi???n ki???m s??t m?? t??i kho???n tr???c thu???c l?? ????n v??? ph??ng nh??ng kh??ng x??c ?????nh ???????c ????n v??? c???p cha");
        // Ng?????c l???i th?? th???c hi???n ti???p

        //Set status active
        registerDecision.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        //Luu so quyet dinh vao bang RegisterDecisionNum
        save(validateDecisionNumWhenCreate(new RegisterDecisionNum(registerDecision)));
        return save(registerDecision);
    }

    @Override
    public RegisterDecision updateRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception {
        ValidateCommon.validateNullObject(registerDecision.getId(), "id");
        RegisterDecision old = registerDecisionRepository.findById(registerDecision.getId()).orElse(null);
        if (old == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        //Validate b???n ghi ph???i ??? tr???ng th??i ??ang ho???t ?????ng m???i ???????c s???a
        if (old.getStatus() == null || old.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
            throw new CommonException(Response.DATA_INVALID, "B???n ghi c???p l???nh c???n s???a kh??ng ??? tr???ng th??i ho???t ?????ng");

        /*
         * Ki???m tra SPP c?? ph???i ph??ng c???a t???nh ho???c c???a c???p cao kh??ng?
         */
        String sppCode = registerDecision.getSppCode();
        Spp spp = cacheService.getSppFromCache(sppCode);
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t??m th???y th??ng tin vi???n ki???m s??t trong Cache");
        // N???u ????ng th?? l???y sppId c???a c???p cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            sppCode = spp.getSppParent();
        if (StringCommon.isNullOrBlank(sppCode))
            throw new CommonException(Response.DATA_NOT_FOUND, "????n v??? vi???n ki???m s??t m?? t??i kho???n tr???c thu???c l?? ????n v??? ph??ng nh??ng kh??ng x??c ?????nh ???????c ????n v??? c???p cha");
        registerDecision.setSppCode(sppCode);
        // Ng?????c l???i th?? th???c hi???n ti???p

        if ((
                registerDecision.getDecisionNum() != null
                        || registerDecision.getIssuesDate() != null)
                && (
                registerDecision.getStatus() == null
                        || registerDecision.getStatus() == old.getStatus())) {
            // Ch??? ki???m tra v?? l??u s??? quy???t ?????nh khi ng?????i d??ng c???p nh???t s??? quy???t ?????nh
            save(validateDecisionNumWhenUpdate(old, new RegisterDecisionNum(registerDecision)));
        }
        return save(old.copyFrom(registerDecision));
    }

    @Override
    public RegisterDecision deleteRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception {
        ValidateCommon.validateNullObject(registerDecision.getId(), "id");
        ValidateCommon.validateNullObject(registerDecision.getReason(), "reason");
        RegisterDecision old = registerDecisionRepository.findById(registerDecision.getId()).orElse(null);
        if (old == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        //Validate b???n ghi ph???i ??? tr???ng th??i ??ang ho???t ?????ng m???i ???????c s???a
        if (old.getStatus() == null || old.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
            throw new CommonException(Response.DATA_INVALID, "B???n ghi c???p l???nh c???n s???a kh??ng ??? tr???ng th??i ho???t ?????ng");
        if (old.getType() == null || old.getType() < SoThuLyConstant.REGISTER_DECISION.TYPE.CASE || old.getType() > SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)
            throw new CommonException(Response.DATA_INVALID, "B???n ghi c???p l???nh kh??ng x??c ?????nh ???????c lo???i n??o");
        //X??a b???n ghi c???p s??? l???nh trong b???ng RegisterDecisionNum
        RegisterDecisionNum registerDecisionNum = null;
        if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.CASE)
            registerDecisionNum = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndCaseCodeAndStatus(old.getSppCode(), old.getDecisionCode(), old.getDecisionNumAuto(), old.getCaseCode(), Constant.STATUS_OBJECT.ACTIVE);
        else if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.ACCUSED)
            registerDecisionNum = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndAccusedCodeAndStatus(old.getSppCode(), old.getDecisionCode(), old.getDecisionNumAuto(), old.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE);
        else if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)
            registerDecisionNum = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndDenouncementIdAndStatus(old.getSppCode(), old.getDecisionCode(), old.getDecisionNumAuto(), old.getDenouncementId(), Constant.STATUS_OBJECT.ACTIVE);
        if (registerDecisionNum != null) {
            registerDecisionNum.setStatus(Constant.STATUS_OBJECT.INACTIVE);
            save(registerDecisionNum);
        }
        old.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        old.setReason(registerDecision.getReason());
        return save(old);
    }

    @Override
    public RegisterDecision findRegisterDecisionAccusedById(RegisterDecision registerDecision) throws Exception {
        ValidateCommon.validateNullObject(registerDecision.getId(), "id");
        RegisterDecision old = registerDecisionRepository.findById(registerDecision.getId()).orElse(null);
        if (old == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        //Th??m th??ng tin chi ti???t c???a v??? ??n, b??? can, quy???t ?????nh
        Case aCase = manageService.findCaseById(new Case(old.getCaseCode()));
        if (aCase != null) old.setCaseName(aCase.getCaseName());
        Accused accused = manageService.findAccusedById(new Accused(old.getAccusedCode()));
        if (accused != null) old.setAccusedName(accused.getFullName());
        Decision decision = cacheService.getDecisionFromCache(old.getDecisionCode());
        if (decision != null) old.setDecisionName(decision.getName());
        return old;
    }

    @Override
    public List<RegisterDecision> getListByAccusedCode(RegisterDecisionRequest registerDecisionRequest) throws Exception {
        ValidateCommon.validateNullObject(registerDecisionRequest.getAccusedCode(), "accusedCode");
        List<RegisterDecision> list = registerDecisionRepository.findByAccusedCodeAndStatus(registerDecisionRequest.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE);
        //Truy v???n d??? li???u v??o pkg
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return list;
    }

    @Override
    public List<RegisterDecision> getList(RegisterDecisionRequest request) throws Exception {
        /*
         * Ki???m tra SPP c?? ph???i ph??ng c???a t???nh ho???c c???a c???p cao kh??ng?
         */
        String sppCode = request.getSppCode();
        Spp spp = cacheService.getSppFromCache(sppCode);
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t??m th???y th??ng tin vi???n ki???m s??t trong Cache");
        // N???u ????ng th?? l???y sppId c???a c???p cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            sppCode = spp.getSppParent();
        if (StringCommon.isNullOrBlank(sppCode))
            throw new CommonException(Response.DATA_NOT_FOUND, "????n v??? vi???n ki???m s??t m?? t??i kho???n tr???c thu???c l?? ????n v??? ph??ng nh??ng kh??ng x??c ?????nh ???????c ????n v??? c???p cha");
        request.setSppCode(sppCode);

        List<RegisterDecision> list;
        if (request.getType() == 3) {
            list = registerDecisionDAO.getListByDenouncement(request);
        } else {
            list = registerDecisionDAO.getList(request);
        }

        //Truy v???n d??? li???u v??o pkg
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        //L???y th??m th??ng tin b??? can ?????u v??? c???a v??? ??n
        list.forEach(x -> {
            if (!StringCommon.isNullOrBlank(x.getCaseCode())) {
                try {
                    Case ccase = manageService.findCaseById(new Case(x.getCaseCode()));
                    if (ccase != null && !StringCommon.isNullOrBlank(ccase.getFirstAcc()))
                        x.setFirstAccused(manageService.findAccusedById(new Accused(ccase.getFirstAcc())));
                } catch (Exception ignored) {
                }
            }
        });
        return list;
    }

    @Override
    public PageResponse getPage(PageRequest pageRequest) throws Exception {
        RegisterDecisionRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), RegisterDecisionRequest.class);
        ValidateCommon.validateNullObject(request.getFromDate(), "fromDate");
        ValidateCommon.validateNullObject(request.getToDate(), "toDate");
        //Truy v???n d??? li???u v??o pkg
        List<RegisterDecision> list = registerDecisionDAO.getList(request);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    @Override
    public Integer getDecisionNum(RegisterDecisionNum registerDecisionNum) throws Exception {
        ValidateCommon.validateNullObject(registerDecisionNum.getSppCode(), "????n v??? ra Q??");
        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionCode(), "T??n quy???t ?????nh");
        ValidateCommon.validateNullObject(registerDecisionNum.getIssuesDate(), "Ng??y c???p s???");
        ValidateCommon.validateNullObject(registerDecisionNum.getType(), "type");

        //Ki???m tra SPP c?? ph???i ph??ng c???a t???nh ho???c c???a c???p cao kh??ng?
        Spp spp = cacheService.getSppFromCache(registerDecisionNum.getSppCode());
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t??m th???y th??ng tin vi???n ki???m s??t trong Cache");
        //N???u ????ng th?? l???y SPP c???p cha g???n nh???t ????? l???y quy???t ?????nh th??? t??? g???n nh???t
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            registerDecisionNum.setSppCode(spp.getSppParent());
        if (StringCommon.isNullOrBlank(registerDecisionNum.getSppCode()))
            throw new CommonException(Response.DATA_NOT_FOUND, "????n v??? vi???n ki???m s??t m?? t??i kho???n tr???c thu???c l?? ????n v??? ph??ng nh??ng kh??ng x??c ?????nh ???????c ????n v??? c???p cha");
        //Ng?????c l???i th?? th???c hi???n ti???p
        Integer decisionNum = registerDecisionNumRepository.getDecisionNumForAccused(
                registerDecisionNum.getDecisionCode(),
                registerDecisionNum.getSppCode(),
                getFromDate(registerDecisionNum.getIssuesDate()),
                getToDate(registerDecisionNum.getIssuesDate()),
                registerDecisionNum.getType(),
                Constant.STATUS_OBJECT.ACTIVE
        );
        if (decisionNum == null) return 1;
        else return decisionNum + 1;
    }

    public static Date getFromDate(Date inputDate) {
        Date date112ToYear = DateCommon.addMonth(DateCommon.getBeginYear(inputDate), 11);
        return (inputDate.before(date112ToYear)) ? DateCommon.addYear(date112ToYear, -1) : date112ToYear;
    }

    public static Date getToDate(Date inputDate) {
        Date date112ToYear = DateCommon.addMonth(DateCommon.getBeginYear(inputDate), 11);
        return (inputDate.before(date112ToYear)) ?
                DateCommon.getEndDay(DateCommon.addDay(date112ToYear, -1)) :
                DateCommon.getEndDay(DateCommon.addDay(DateCommon.addYear(date112ToYear, 1), -1));
    }

    private RegisterDecisionNum validateDecisionNumWhenCreate(RegisterDecisionNum registerDecisionNum) throws Exception {
        //Validate ng??y c???n c???p l???nh ???? ???????c c???p l???nh ch??a?
        if (registerDecisionNum.getType() == 1) {
            // V??? ??n
            if (registerDecisionRepository.existsAllByCaseCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getCaseCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "S??? quy???t ?????nh ???? ???????c c???p trong ng??y");
        } else if (registerDecisionNum.getType() == 2) {
            // B??? can
            if (registerDecisionRepository.existsAllByAccusedCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getAccusedCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "S??? quy???t ?????nh ???? ???????c c???p trong ng??y");
        } else {
            if (registerDecisionRepository.existsAllByDenouncementIdAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getDenouncementId(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "S??? quy???t ?????nh ???? ???????c c???p trong ng??y");
        }

        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionNum(), "decisionNum");
        if (registerDecisionNum.getDecisionNum() < 1)
            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh kh??ng ???????c nh??? h??n 1");
        Integer decisionNum = getDecisionNum(registerDecisionNum);
        if (decisionNum == 1)
            return registerDecisionNum;
//        else if (!registerDecisionNum.getDecisionNum().equals(decisionNum))
//            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh kh??ng ????ng th??? t???");
        RegisterDecisionNum decisionNumOld = registerDecisionNumRepository.findDecisionNum(
                registerDecisionNum.getDecisionCode(),
                registerDecisionNum.getSppCode(),
                getFromDate(registerDecisionNum.getIssuesDate()),
                getToDate(registerDecisionNum.getIssuesDate()),
                registerDecisionNum.getType(),
                Constant.STATUS_OBJECT.ACTIVE,
                registerDecisionNum.getDecisionNum()
        );
        if (decisionNumOld != null)
            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh ???? ???????c c???p trong n??m");
        return registerDecisionNum;
    }

    private RegisterDecisionNum validateDecisionNumWhenUpdate(RegisterDecision registerDecision, RegisterDecisionNum registerDecisionNum) throws Exception {
        //Validate ng??y c???n c???p l???nh ???? ???????c c???p l???nh ch??a?
        RegisterDecision old;

        if (registerDecisionNum.getType() == 1) {
            old = registerDecisionRepository.findFirstByCaseCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getCaseCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        } else if (registerDecisionNum.getType() == 2) {
            old = registerDecisionRepository.findFirstByAccusedCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getAccusedCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        } else {
            old = registerDecisionRepository.findFirstByDenouncementIdAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getDenouncementId(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        }
        if (old != null && old.getId().compareTo(registerDecision.getId()) != 0)
            throw new CommonException(Response.OBJECT_IS_EXISTS, "S??? quy???t ?????nh ???? ???????c c???p trong ng??y");

        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionNum(), "decisionNum");
        if (registerDecisionNum.getDecisionNum() < 1)
            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh kh??ng ???????c nh??? h??n 1");
        Integer decisionNum = getDecisionNum(registerDecisionNum);
        if (decisionNum == 1)
            return registerDecisionNum;
//        else if (old != null &&
//                !registerDecisionNum.getDecisionNum().equals(decisionNum)
//                && !registerDecisionNum.getDecisionNum().equals(old.getDecisionNum())
//        )
//            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh kh??ng ????ng th??? t???");
        RegisterDecisionNum decisionNumOld = registerDecisionNumRepository.findDecisionNum(
                registerDecisionNum.getDecisionCode(),
                registerDecisionNum.getSppCode(),
                getFromDate(registerDecisionNum.getIssuesDate()),
                getToDate(registerDecisionNum.getIssuesDate()),
                registerDecisionNum.getType(),
                Constant.STATUS_OBJECT.ACTIVE,
                registerDecisionNum.getDecisionNum()
        );
        if (decisionNumOld != null)
            throw new CommonException(Response.DATA_INVALID, "S??? quy???t ?????nh ???? ???????c c???p trong n??m");
        //X??a b???n ghi c???p s??? l???nh trong b???ng RegisterDecisionNum
        RegisterDecisionNum registerDecisionNumOld = null;
        if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.CASE)
            registerDecisionNumOld = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndCaseCodeAndStatus(
                    old.getSppCode(),
                    old.getDecisionCode(),
                    old.getDecisionNumAuto(),
                    old.getCaseCode(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        else if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.ACCUSED)
            registerDecisionNumOld = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndAccusedCodeAndStatus(
                    old.getSppCode(),
                    old.getDecisionCode(),
                    old.getDecisionNumAuto(),
                    old.getAccusedCode(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        else if (old.getType() == SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)
            registerDecisionNumOld = registerDecisionNumRepository.findFirstBySppCodeAndDecisionCodeAndDecisionNumAndDenouncementIdAndStatus(
                    old.getSppCode(),
                    old.getDecisionCode(),
                    old.getDecisionNumAuto(),
                    old.getDenouncementId(),
                    Constant.STATUS_OBJECT.ACTIVE
            );
        if (registerDecisionNumOld != null) {
//            registerDecisionNum.setStatus(Constant.STATUS_OBJECT.INACTIVE);
            save(registerDecisionNumOld);
        }
        return registerDecisionNum;
    }

    @Override
    public Response checkRegisterDecision(String req, Integer type) throws Exception {
        if (type == 1) {
            // V??? ??n
            if (registerDecisionRepository.existsAllByCaseCodeAndTypeAndStatus(req, type, Constant.STATUS_OBJECT.ACTIVE))
                throw new CommonException(Response.OBJECT_IS_EXISTS, "V??? ??n ???? ???????c c???p s??? l???nh");
        } else if (type == 2) {
            // B??? can
            if (registerDecisionRepository.existsAllByAccusedCodeAndTypeAndStatus(req, type, Constant.STATUS_OBJECT.ACTIVE))
                throw new CommonException(Response.OBJECT_IS_EXISTS, "B??? can ???? ???????c c???p s??? l???nh");
        }
        return null;
    }
}

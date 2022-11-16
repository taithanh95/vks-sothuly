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
        ValidateCommon.validateNullObject(registerDecision.getStage(), "Giai đoạn");
        ValidateCommon.validateNullObject(registerDecision.getSppCode(), "Đơn vị ra QĐ");
        ValidateCommon.validateNullObject(registerDecision.getDecisionCode(), "Tên quyết định");
        ValidateCommon.validateNullObject(registerDecision.getIssuesDate(), "Ngày cấp số");
//        ValidateCommon.validateNullObject(registerDecision.getDecisionNum(), "Số QĐ thực thế");
        ValidateCommon.validateNullObject(registerDecision.getDecisionNumAuto(), "Số QĐ");
        if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.CASE)) {
            ValidateCommon.validateNullObject(registerDecision.getCaseCode(), "Mã vụ án");
        } else if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.ACCUSED)) {
            ValidateCommon.validateNullObject(registerDecision.getCaseCode(), "Mã vụ án");
            ValidateCommon.validateNullObject(registerDecision.getAccusedCode(), "Mã bị can");
        } else if (registerDecision.getType().equals(SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)) {
            ValidateCommon.validateNullObject(registerDecision.getDenouncementId(), "Mã tin báo");
        }

        // Kiểm tra SPP có phải phòng của tỉnh hoặc của cấp cao không?
        Spp spp = cacheService.getSppFromCache(registerDecision.getSppCode());
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Không tìm thấy thông tin viện kiểm sát trong Cache");
        // Nếu đúng thì lấy sppId của cấp cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            registerDecision.setSppCode(spp.getSppParent());

        if (StringCommon.isNullOrBlank(registerDecision.getSppCode()))
            throw new CommonException(Response.DATA_NOT_FOUND, "Đơn vị viện kiểm sát mà tài khoản trực thuộc là đơn vị phòng nhưng không xác định được đơn vị cấp cha");
        // Ngược lại thì thực hiện tiếp

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
        //Validate bản ghi phải ở trạng thái đang hoạt động mới được sửa
        if (old.getStatus() == null || old.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
            throw new CommonException(Response.DATA_INVALID, "Bản ghi cấp lệnh cần sửa không ở trạng thái hoạt động");

        /*
         * Kiểm tra SPP có phải phòng của tỉnh hoặc của cấp cao không?
         */
        String sppCode = registerDecision.getSppCode();
        Spp spp = cacheService.getSppFromCache(sppCode);
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Không tìm thấy thông tin viện kiểm sát trong Cache");
        // Nếu đúng thì lấy sppId của cấp cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            sppCode = spp.getSppParent();
        if (StringCommon.isNullOrBlank(sppCode))
            throw new CommonException(Response.DATA_NOT_FOUND, "Đơn vị viện kiểm sát mà tài khoản trực thuộc là đơn vị phòng nhưng không xác định được đơn vị cấp cha");
        registerDecision.setSppCode(sppCode);
        // Ngược lại thì thực hiện tiếp

        if ((
                registerDecision.getDecisionNum() != null
                        || registerDecision.getIssuesDate() != null)
                && (
                registerDecision.getStatus() == null
                        || registerDecision.getStatus() == old.getStatus())) {
            // Chỉ kiểm tra và lưu số quyết định khi người dùng cập nhật số quyết định
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
        //Validate bản ghi phải ở trạng thái đang hoạt động mới được sửa
        if (old.getStatus() == null || old.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
            throw new CommonException(Response.DATA_INVALID, "Bản ghi cấp lệnh cần sửa không ở trạng thái hoạt động");
        if (old.getType() == null || old.getType() < SoThuLyConstant.REGISTER_DECISION.TYPE.CASE || old.getType() > SoThuLyConstant.REGISTER_DECISION.TYPE.DENOUNCEMENT)
            throw new CommonException(Response.DATA_INVALID, "Bản ghi cấp lệnh không xác định được loại nào");
        //Xóa bản ghi cấp số lệnh trong bảng RegisterDecisionNum
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
        //Thêm thông tin chi tiết của vụ án, bị can, quyết định
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
        //Truy vấn dữ liệu vào pkg
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return list;
    }

    @Override
    public List<RegisterDecision> getList(RegisterDecisionRequest request) throws Exception {
        /*
         * Kiểm tra SPP có phải phòng của tỉnh hoặc của cấp cao không?
         */
        String sppCode = request.getSppCode();
        Spp spp = cacheService.getSppFromCache(sppCode);
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Không tìm thấy thông tin viện kiểm sát trong Cache");
        // Nếu đúng thì lấy sppId của cấp cha
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            sppCode = spp.getSppParent();
        if (StringCommon.isNullOrBlank(sppCode))
            throw new CommonException(Response.DATA_NOT_FOUND, "Đơn vị viện kiểm sát mà tài khoản trực thuộc là đơn vị phòng nhưng không xác định được đơn vị cấp cha");
        request.setSppCode(sppCode);

        List<RegisterDecision> list;
        if (request.getType() == 3) {
            list = registerDecisionDAO.getListByDenouncement(request);
        } else {
            list = registerDecisionDAO.getList(request);
        }

        //Truy vấn dữ liệu vào pkg
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        //Lấy thêm thông tin bị can đầu vụ của vụ án
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
        //Truy vấn dữ liệu vào pkg
        List<RegisterDecision> list = registerDecisionDAO.getList(request);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    @Override
    public Integer getDecisionNum(RegisterDecisionNum registerDecisionNum) throws Exception {
        ValidateCommon.validateNullObject(registerDecisionNum.getSppCode(), "Đơn vị ra QĐ");
        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionCode(), "Tên quyết định");
        ValidateCommon.validateNullObject(registerDecisionNum.getIssuesDate(), "Ngày cấp số");
        ValidateCommon.validateNullObject(registerDecisionNum.getType(), "type");

        //Kiểm tra SPP có phải phòng của tỉnh hoặc của cấp cao không?
        Spp spp = cacheService.getSppFromCache(registerDecisionNum.getSppCode());
        if (spp == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Không tìm thấy thông tin viện kiểm sát trong Cache");
        //Nếu đúng thì lấy SPP cấp cha gần nhất để lấy quyết định thứ tự gần nhất
        if (spp.getIsDePart() != null && spp.getIsDePart().equals("Y"))
            registerDecisionNum.setSppCode(spp.getSppParent());
        if (StringCommon.isNullOrBlank(registerDecisionNum.getSppCode()))
            throw new CommonException(Response.DATA_NOT_FOUND, "Đơn vị viện kiểm sát mà tài khoản trực thuộc là đơn vị phòng nhưng không xác định được đơn vị cấp cha");
        //Ngược lại thì thực hiện tiếp
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
        //Validate ngày cần cấp lệnh đã được cấp lệnh chưa?
        if (registerDecisionNum.getType() == 1) {
            // Vụ án
            if (registerDecisionRepository.existsAllByCaseCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getCaseCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "Số quyết định đã được cấp trong ngày");
        } else if (registerDecisionNum.getType() == 2) {
            // Bị can
            if (registerDecisionRepository.existsAllByAccusedCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getAccusedCode(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "Số quyết định đã được cấp trong ngày");
        } else {
            if (registerDecisionRepository.existsAllByDenouncementIdAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
                    registerDecisionNum.getDenouncementId(),
                    registerDecisionNum.getSppCode(),
                    registerDecisionNum.getDecisionCode(),
                    registerDecisionNum.getIssuesDate(),
                    registerDecisionNum.getType(),
                    Constant.STATUS_OBJECT.ACTIVE
            )) throw new CommonException(Response.OBJECT_IS_EXISTS, "Số quyết định đã được cấp trong ngày");
        }

        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionNum(), "decisionNum");
        if (registerDecisionNum.getDecisionNum() < 1)
            throw new CommonException(Response.DATA_INVALID, "Số quyết định không được nhỏ hơn 1");
        Integer decisionNum = getDecisionNum(registerDecisionNum);
        if (decisionNum == 1)
            return registerDecisionNum;
//        else if (!registerDecisionNum.getDecisionNum().equals(decisionNum))
//            throw new CommonException(Response.DATA_INVALID, "Số quyết định không đúng thứ tự");
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
            throw new CommonException(Response.DATA_INVALID, "Số quyết định đã được cấp trong năm");
        return registerDecisionNum;
    }

    private RegisterDecisionNum validateDecisionNumWhenUpdate(RegisterDecision registerDecision, RegisterDecisionNum registerDecisionNum) throws Exception {
        //Validate ngày cần cấp lệnh đã được cấp lệnh chưa?
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
            throw new CommonException(Response.OBJECT_IS_EXISTS, "Số quyết định đã được cấp trong ngày");

        ValidateCommon.validateNullObject(registerDecisionNum.getDecisionNum(), "decisionNum");
        if (registerDecisionNum.getDecisionNum() < 1)
            throw new CommonException(Response.DATA_INVALID, "Số quyết định không được nhỏ hơn 1");
        Integer decisionNum = getDecisionNum(registerDecisionNum);
        if (decisionNum == 1)
            return registerDecisionNum;
//        else if (old != null &&
//                !registerDecisionNum.getDecisionNum().equals(decisionNum)
//                && !registerDecisionNum.getDecisionNum().equals(old.getDecisionNum())
//        )
//            throw new CommonException(Response.DATA_INVALID, "Số quyết định không đúng thứ tự");
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
            throw new CommonException(Response.DATA_INVALID, "Số quyết định đã được cấp trong năm");
        //Xóa bản ghi cấp số lệnh trong bảng RegisterDecisionNum
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
            // Vụ án
            if (registerDecisionRepository.existsAllByCaseCodeAndTypeAndStatus(req, type, Constant.STATUS_OBJECT.ACTIVE))
                throw new CommonException(Response.OBJECT_IS_EXISTS, "Vụ án đã được cấp số lệnh");
        } else if (type == 2) {
            // Bị can
            if (registerDecisionRepository.existsAllByAccusedCodeAndTypeAndStatus(req, type, Constant.STATUS_OBJECT.ACTIVE))
                throw new CommonException(Response.OBJECT_IS_EXISTS, "Bị can đã được cấp số lệnh");
        }
        return null;
    }
}

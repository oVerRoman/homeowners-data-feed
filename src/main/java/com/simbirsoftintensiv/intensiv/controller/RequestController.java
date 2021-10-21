package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.repository.RequestRepository;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Обрабатываем запросы от фронта
 *
 * getAllRequest - Получаем данные о заявках пользователей
 * GET на адрес /request - возвращает JSON со списком запросов пользователей
 * поддерживается фильтрация по type, title, date, addressid, comment, status, clientid
 * поддерживается пагинация, по умолчанию 5 записей на страницу, начальный вывод с нулевой
 * поддерживается сортировк, необходимо передать название поля
 *
 * getRequestById - Получаем данные заказа по его id
 * GET на адрес /request/{id} - возвращает JSON с данными заявки клиента
 *
 * getRequestCount - Возвращает количество данных отвечающим условиям запроса (используем для пагинации на фронте)
 * GET на адрес /request/count
 * параметры запроса аналогично getAllRequest
 *
 * createRequest - Создаем заявку клиента
 * POST запрос на адрес /request - возвращает JSON с созданным запросом
 * ождиает в данных запроса JSON с данными о заявке
 *
 * updateRequest - Обновление завки клиента
 * POST запрос на адрес /request/{id} - возвращает обновленные данные по завке
 * ожидает в данных запроса JSON с данными о заявке, обновляет только то что не NULL
 *
 * deleteById - Удалет заявку клиента
 * DELETE - запрос на адрес /request/{id} - возвращает код ответа на запрос
 * ожидает id заявки
 *
 */

@RestController
@RequestMapping(path = "rest/request")
public class RequestController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    static final Logger log =
            LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    // Получаем id пользователя на основе роли
    private Integer getUserIdByRole(UserDetails details, Integer userId){
        if (details.getAuthorities().toString().equals("[USER]")) {
            return userService.getByPhone(Long.parseLong(details.getUsername())).getId();
        } else {
            return userId;
        }
    }

    // получаем все запросы клиентов
    // ? Как фронт передает дату. В каком формате.
    @GetMapping(path = "")
    public @ResponseBody
    ResponseEntity<List<Request>> getAllRequest(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "startdate", required = false) String startDate,
            @RequestParam(value = "enddate", required = false) String endDate,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "clientId", required = false) Integer clientId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "date") String order,
            @AuthenticationPrincipal UserDetails userDetails){

        LocalDateTime dateStart = null;
        LocalDateTime dateEnd = null;
        if (startDate != null) {
            dateStart = LocalDateTime.parse(startDate, formatter);
        }
        if (endDate != null) {
            dateEnd = LocalDateTime.parse(endDate, formatter);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order).descending());
        List<Request> result = requestRepository.findAllBy(type,
                status,
                dateStart,
                dateEnd,
                getUserIdByRole(userDetails,clientId),
                pageable).getContent();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Получаем запрос килента по его id
    @GetMapping(path = "{id}")
    public @ResponseBody
    ResponseEntity<Request> getRequestById(@PathVariable Integer id,  @AuthenticationPrincipal UserDetails userDetails) {
        if (id>0) {
            if (requestRepository.findById(id).isPresent()) {
                final Request result = requestRepository.findById(id).get();
                final Integer userId = getUserIdByRole(userDetails,result.getClient());
                if (userId.equals(result.getClient())) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Получаем колличество обращений клиентов
    @GetMapping(path = "/count")
    public @ResponseBody
    Long getRequestCount(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "startdate", required = false) String startDate,
            @RequestParam(value = "enddate", required = false) String endDate,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "clientId", required = false) Integer clientId,
            @AuthenticationPrincipal UserDetails userDetails){

        LocalDateTime dateStart = null;
        LocalDateTime dateEnd = null;
        if (startDate != null) {
            dateStart = LocalDateTime.parse(startDate, formatter);
        }
        if (endDate != null) {
            dateEnd = LocalDateTime.parse(endDate, formatter);
        }
        return requestRepository.countAllBy(type, status,
                dateStart,
                dateEnd,
                getUserIdByRole(userDetails,clientId));
    }

    // Добавление обращения пользователя
    @PostMapping(path = "")
    public ResponseEntity<?> createRequest(@RequestBody Request request,  @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (request != null) {
                request.setClient(getUserIdByRole(userDetails,request.getClient()));
                log.info("Request creat " + request.getId() + " .");
                final Request result = requestRepository.save(request);
                return  new ResponseEntity<>(result,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Обновление обращения клиента
    @PostMapping(path = "{id}")
    public ResponseEntity<?> updateRequest(@PathVariable(name = "id")  Integer id,
                                           @RequestBody Request request,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (requestRepository.findById(id).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if (!request.isNull()) {
            try {
                Request request1 = requestRepository.findById(id).get();
                if (!request1.getClient().equals(getUserIdByRole(userDetails,request1.getClient()))) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                if (request.getStatus() != null) {
                    request1.setStatus(request.getStatus());
                }
                if (request.getAddress() != null) {
                    request1.setAddress(request.getAddress());
                }
                if (request.getClient() != null) {
                    request1.setClient(request.getClient());
                }
                if (request.getComment() != null) {
                    request1.setComment(request.getComment());
                }
                if (request.getDate() != null) {
                    request1.setDate(request.getDate());
                }
                if (request.getTitle() != null) {
                    request1.setTitle(request.getTitle());
                }
                if (request.getType() != null) {
                    request1.setType(request.getType());
                }
                if (request.getFileName() != null) {
                    request1.setFileName(request.getFileName());
                }
                final Request result = requestRepository.save(request1);
                log.info("UpdateRequest " + request.getId() + ".");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Удаление обращения клиента
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id,  @AuthenticationPrincipal UserDetails userDetails) {
        if (id > 0) {
            if (requestRepository.findById(id).isPresent()) {
                    if (!requestRepository.findById(id).get().getClient().equals(getUserIdByRole(userDetails,
                            requestRepository.findById(id).get().getClient()))) {
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    }
                    requestRepository.deleteById(id);
                    log.warn("Deleting a client's request " + id + ".");
                    return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("attempt to Delete a client's request "+ id +" .");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

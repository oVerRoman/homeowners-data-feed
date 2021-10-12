package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.RequestRepository;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
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

@Controller
@RequestMapping(path = "request")
public class RequestController {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    // получаем все запросы клиентов
    // ? Как фронт передает дату. В каком формате.
    @GetMapping(path = "")
    public @ResponseBody
    ResponseEntity<List<Request>> getAllRequest(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) Instant date,
            @RequestParam(value = "address", required = false) Integer address,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "clientId", required = false) Integer clientId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "id") String order,
            @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.getByPhone(Long.parseLong(userDetails.getUsername()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(order).descending());
        List<Request> result = requestRepository.findAllBy(status,pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Получаем запрос килента по его id
    @GetMapping(path = "{id}")
    public @ResponseBody
    ResponseEntity<Request> getRequestById(@PathVariable Integer id,  @AuthenticationPrincipal UserDetails user) {
        if (id>0) {
            if (requestRepository.findById(id).isPresent()) {
                final Request result = requestRepository.findById(id).get();
                return new ResponseEntity<>(result, HttpStatus.OK);
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
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) Instant date,
            @RequestParam(value = "address", required = false) Address address,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "clientId", required = false) User userid,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "id") String order,
            @AuthenticationPrincipal UserDetails user){
        return requestRepository.countAllBy();
    }

    // Добавление обращения пользователя
    @PostMapping(path = "")
    public ResponseEntity<?> createRequest(@RequestBody Request request,  @AuthenticationPrincipal UserDetails user) {

        //request.setClient();
        System.out.println(request.toString());
        final Request result = requestRepository.save(request);
      //  result.setClient(user);
        return  new ResponseEntity<>(result,HttpStatus.OK);
     /*   try {
            if (request != null) {
             //   final Request result = requestRepository.save(request);
            //    return  new ResponseEntity<>(result,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } */
    }

    // Обновление обращения клиента
    @PostMapping(path = "{id}")
    public ResponseEntity<?> updateRequest(@PathVariable(name = "id")  Integer id,
                                           @RequestBody Request request,
                                           @AuthenticationPrincipal UserDetails user) {
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
                final Request result = requestRepository.save(request1);
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
    public ResponseEntity<?> deleteById(@PathVariable Integer id,  @AuthenticationPrincipal UserDetails user) {
        if (id > 0) {
            if (requestRepository.findById(id).isPresent()) {
                requestRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

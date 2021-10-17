package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.controller.LoginController;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/rest/admin/users")
public class AdminRestController {
    static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserTo> getAll() {
        log.info("The admin has received data for all users.");
        //TODO а если несколько админов то надо будет сделать какой конкретно
        return userService.getAll()
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{phone}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phone") long phone) {
//        User user = userService.getByPhone(phone);
//        if (user == null) {
//            log.warn("Попытка удаление не существующего пользователя.");
//            throw new NotFoundException("User" + phone + " not find ");
//        }

        log.info("User " + phone + "deleting.");
        userService.delete(phone);
    }

    @GetMapping("/{phone}")
    public UserTo get(@PathVariable("phone") long phone) {
//        User user = userService.getByPhone(phone);
//        if (user == null) {
//            log.warn("Попытка получение данных не существующего пользователя.");
//            throw new NotFoundException("User" + phone + " not find ");
//        }
        log.info("Админ получил данные на пользователя " + phone);
        return UserUtil.asTo(userService.getByPhone(phone));
    }

    @ResponseBody
    @GetMapping("/count")
    public HashMap<String, Integer> getUsersCount() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer countUsers = userService.getAll().size();
        map.put("allUsers", countUsers);
        log.info("The admin requested the number of users.");

        return map;
    }

    @ResponseBody
    @GetMapping("/logi")
    public List getlogInfo(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        //rest/admin/users/logi
        log.info("The admin looks at the logs.");
        List<String> list = null;
        try {
            Path path = Path.of("appintensiv.log");
            list = Files.readAllLines(path);
        } catch (Exception e) {
            log.warn("not find file", e);
        }
        Integer from = pageNumber*pageSize;
        Integer to = from + pageSize;
        List answer = list.subList(from,to);
        System.out.println("list" + list.size());
        System.out.println("answer" + answer.size());
        return answer;
    }

    @ResponseBody
    @GetMapping("/givemelogs")
    public List getAllLogInfo() {

        log.info("The admin looks at the logs.");
        List<String> list = null;
        try {
            Path path = Path.of("appintensiv.log");
            list = Files.readAllLines(path);
        } catch (Exception e) {
            log.warn("Not find file", e);
        }

        return list;
    }
}

package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*
После авторизации Юзера и если у него роль ADMIN
Фронт делает GET запрос на /admin
и ему приходит список/json со списком юзеров в БД
при POST запросе на /admin?action=delete&userId={userId}
происходит удаление
 */


@RestController
@RequestMapping(value = "/rest/admin/users")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserTo> getAll() {
        return userService.getAll()
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }
   /* @ResponseBody
    @GetMapping
    public HashMap<String, List<User>> getAll() {
        System.out.println("создаем массив с логинами юзеров");
        HashMap<String, List<User>> map = new HashMap<>();
        map.put("Content", userService.getAll());
        return map;
    }*/

    @DeleteMapping("/{phone}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phone") long phone) {
        userService.delete(phone);
    }
/*
    @PostMapping("/admin")
    public HashMap<String, String> deleteUser(@RequestParam(required = true, defaultValue = "") Integer userId,
                                              @RequestParam(defaultValue = "") String action) {

        HashMap<String, String> map = new HashMap<>();
        if (action.equals("DELETE")) {
            boolean itDelete = userService.delete(userId);
            if (itDelete) {
                map.put("code", "200 OK");
            } else {
                map.put("code", "404");
            }
        }
        return map;
    }
*/
    @GetMapping("/{phone}")
    public UserTo get(@PathVariable("phone") long phone) {
        return UserUtil.asTo(userService.getByPhone(phone));
    }
/*
    @GetMapping("/user/{phone}")
    public HashMap gtUser(@PathVariable("userId") String userId) {
        Integer rightId;
        List<String> code;
        HashMap<String, List> map = new HashMap<>();
        try {
            rightId = Integer.parseInt(userId);
        } catch (NumberFormatException exception) {
            code = Collections.singletonList("400");
            map.put("code", code);
            return map;
        }
        List User = userService.usergtList(rightId);

        if (getAll().size() == 1) {
            code = Collections.singletonList("404");
        } else {
            System.out.println(getAll().size());
            code = Collections.singletonList("200");
        }
        map.put("Content", User);
        map.put("code", code);
        return map;
    }*/

    @ResponseBody
    @GetMapping("/admin/count")
    public HashMap<String, Integer> getUsersCount() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer countUsers = userService.getAll().size();
        map.put("code", 200);
        map.put("Content", countUsers);
        return map;
    }

}

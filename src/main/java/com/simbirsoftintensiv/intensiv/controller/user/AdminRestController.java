package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Админский user контроллер")
@RestController
@RequestMapping(value = "/rest/admin/users")
public class AdminRestController {

    static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserTo> getAll(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Admin:{} {} has received data for all users", authorizedUser.getUserTo().getFirstName(),
                authorizedUser.getUserTo().getSecondName());
        return userService.getAll()
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{phone}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phone") long phone,
                       @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Admin:{} {} has deleted user with phone:{}", authorizedUser.getUserTo().getFirstName(),
                authorizedUser.getUserTo().getSecondName(), userService.getByPhone(phone).getPhone());
        userService.delete(phone);
    }

    @GetMapping("/{phone}")
    public UserTo get(@PathVariable("phone") long phone,
                      @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Admin:{} {} has received user with phone:{}", authorizedUser.getUserTo().getFirstName(),
                authorizedUser.getUserTo().getSecondName(), userService.getByPhone(phone).getPhone());
        return UserUtil.asTo(userService.getByPhone(phone));
    }

    @ResponseBody
    @GetMapping("/count")
    public HashMap<String, Integer> getUsersCount(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        HashMap<String, Integer> map = new HashMap<>();
        Integer countUsers = userService.getAll().size();
        map.put("allUsers", countUsers);
        log.info("Admin:{} {} requested the number of users", authorizedUser.getUserTo().getFirstName(),
                authorizedUser.getUserTo().getSecondName());
        return map;
    }

    @ResponseBody
    @GetMapping("/logi")
    public List<String> getlogInfo(
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
        int from = pageNumber * pageSize;
        int to = from + pageSize;
        assert list != null;
        return list.subList(from, to);
    }

    @ResponseBody
    @GetMapping("/givemelogs")
    public List<String> getAllLogInfo() {

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

package com.example.accesskeybackend.template.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/web")
public class IPv6Controller {

    @PostMapping("/checkIpv6Support")
    public ResponseEntity<?> checkIpv6Support(@RequestBody String siteUrl) throws URISyntaxException {
        boolean success = false;

        Pattern pattern = Pattern.compile("\\w+:(\\/?\\/?)[^\\s]+");
        Matcher matcher = pattern.matcher(siteUrl);

        if(matcher.matches()) {
            URI uri = new URI(siteUrl);
            siteUrl = uri.getHost();
        }

        try {
            for(InetAddress address : InetAddress.getAllByName(siteUrl)) {
                if (address instanceof Inet6Address) {
                    success = true;
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}

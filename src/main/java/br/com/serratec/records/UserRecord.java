package br.com.serratec.records;

import java.util.Set;

public record UserRecord(
        String login,
        String password,
        Set<String> role
) {

}

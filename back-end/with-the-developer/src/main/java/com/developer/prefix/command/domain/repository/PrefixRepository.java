package com.developer.prefix.command.domain.repository;

import com.developer.prefix.command.domain.aggregate.Prefix;

import java.util.Optional;

public interface PrefixRepository {

    Prefix save(Prefix prefix);
}

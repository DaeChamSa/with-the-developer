package com.developer.postservice.prefix.command.domain.repository;

import com.developer.postservice.prefix.command.domain.aggregate.Prefix;
import com.developer.postservice.prefix.command.domain.aggregate.Prefix;

import java.util.Optional;

public interface PrefixRepository {

    Prefix save(Prefix prefix);
}

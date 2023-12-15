package org.mundiapolis.digitalbankbackend.repositories;

import org.mundiapolis.digitalbankbackend.entities.AccountOperation;
import org.mundiapolis.digitalbankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}

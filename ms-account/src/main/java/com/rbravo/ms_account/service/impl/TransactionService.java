package com.rbravo.ms_account.service.impl;

import com.rbravo.ms_account.exception.AccountNotFoundException;
import com.rbravo.ms_account.mapper.TransactionMapper;
import com.rbravo.ms_account.model.dto.TransactionDTO;
import com.rbravo.ms_account.model.entity.Account;
import com.rbravo.ms_account.model.entity.Transaction;
import com.rbravo.ms_account.model.enums.TransactionTypeEnum;
import com.rbravo.ms_account.repository.IAccountRepository;
import com.rbravo.ms_account.repository.ITransactionRepository;
import com.rbravo.ms_account.service.ITransactionService;
import com.rbravo.ms_account.service.ITransactionSolverService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service a transaction
 *
 * @author rbravo
 */
@Service
@Lazy
public class TransactionService implements ITransactionService {

    private final TransactionMapper transactionMapper;
    private final ITransactionRepository repository;
    private final IAccountRepository accountRepository;
    private final ITransactionSolverService transactionSolver;

    public TransactionService(TransactionMapper transactionMapper,
                              ITransactionRepository repository,
                              IAccountRepository accountRepository,
                              ITransactionSolverService transactionSolver) {
        this.transactionMapper = transactionMapper;
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transactionSolver = transactionSolver;
    }

    @Override
    public TransactionDTO create(TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(transactionDTO.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Transaction transaction = transactionSolver.solve(account,
                TransactionTypeEnum.valueOf(transactionDTO.getTransactionType()),
                transactionDTO.getAmount());
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO findById(Long id) {
        return repository.findById(id).map(transactionMapper::toDTO).orElseThrow(() -> new AccountNotFoundException("Account not found"));

    }

    @Override
    public List<TransactionDTO> findAll() {
        return repository.findAll().stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public TransactionDTO update(Long id, TransactionDTO transactionDto) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new IllegalStateException("Transaction not found"));
        transactionMapper.toEntityUpdate(transactionDto, transaction);
        Transaction savedTransaction = repository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}

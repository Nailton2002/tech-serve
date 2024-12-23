package com.nailton.techserve.intrastructure.exceptions

class ResourceNotFoundException(message: String) : RuntimeException(message)

class BusinessRuleException(message: String) : RuntimeException(message)

class DataIntegrityException(message: String) : RuntimeException(message)

class DatabaseException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

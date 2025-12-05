# PackStatusPlaceholder
Tracks players' resource-pack status and runs configurable console commands (and provides a PlaceholderAPI "pack_status" value).

`%pack_status%` returns true/false depending on pack status.

Native 1.20.3, tested on 1.21.1 & 1.21.10

## Config
```yml
# Should the status be logged in the console?
# i.e. "USERNAME Pack Accepted"
log: false

# Delay in ticks before executing commands
delay: 20 # 20 = 1 second

# Command sent per events, sent through console.
## %username%, %uuid%, %s (alias for %username%)
# [] = No commands
commands:
  join: []
  quit: []
  accepted: []
  loaded: []
  declined:
    - 'kick %s You''ve declined the resource pack.\nIn order to play you must accept it.'
  failed: []
  downloaded: []
  invalid-url: []
  discarded: []
  reload-failed: []
```

## Shaded classes
- [`valorless/valorlessutils/ValorlessUtils.java`](https://github.com/Valorless/ValorlessUtils/tree/main/src/main/java/valorless/valorlessutils/ValorlessUtils.java) Used for logging.
- [`valorless/valorlessutils/config/Config.java`](https://github.com/Valorless/ValorlessUtils/tree/main/src/main/java/valorless/valorlessutils/config/Config.java) Used for configs.
- [`valorless/valorlessutils/file/**`](https://github.com/Valorless/ValorlessUtils/tree/main/src/main/java/valorless/valorlessutils/file) Used for configs.
- [`valorless/valorlessutils/utils/Utils.java`](https://github.com/Valorless/ValorlessUtils/tree/main/src/main/java/valorless/valorlessutils/utils/Utils.java) Used for logging and configs.


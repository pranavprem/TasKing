# TasKing agent guide

TasKing is a Swing desktop app. Persistence is a folder of JSON files, not a database. Read this file before changing behavior.

## Architecture

`controller.Main` is the entry point.

- No arguments: open `gui.JLogin`. A successful login opens `gui.JTasking` and hides the login window.
- `New`: prompt for id, password, name, and manager id, then `services.NewUserService`.
- `Flush`: `services.BackupService.flush()`, which rewrites task files to `[]`.

The board does not talk to the files itself. Buttons and menus go through `events.*` to `services.*` to `dao.*`.

- `LoginService` checks passwords, knows who reports to whom, changes passwords, and builds the CSV export.
- `TaskService` adds, edits, deletes, completes, pulls, and releases tasks.
- `TaskDAO` / `UserDAO` map those operations onto JSON files via Jackson.
- `ExportDAO` writes the CSV into the data folder.

Action buttons do not parse their tooltip text. Each button carries `action`, `storeKey`, `taskId`, and sometimes `row` or `draft` as Swing client properties. Tooltips are only the hover text. An ADD button saves the draft attached to that button, not every draft on the page.

## Data files

`util.Utilities.getLocation()` chooses the folder:

1. system property `tasking.dataDir`
2. environment variable `TASKING_DATA_DIR`
3. first line of `location.txt` in the working directory
4. `data/` under the working directory

The returned path always ends with a separator, and the directory is created if needed.

| Key passed to the DAO | File |
| --- | --- |
| (users) | `User.json` |
| `{id}` | that person's open tasks |
| `{manager}-{id}` | tasks assigned by that manager |
| `{id}-completed` | finished tasks |
| `{manager}-pool` | shared pool |

`tasknum.txt` in the same folder is the integer counter. `Utilities.nextTaskId()` increments it. `peekNextTaskId()` does not.

A pool task that has been pulled has `--POOLTASK` appended to its name (`util.Constants.POOL_MARKER`). Release strips that suffix. The marker is 10 characters; do not go back to deleting a fixed number of characters from an arbitrary name.

`ownerId` for a completed task is the id after the last `-`, unless that tail is `pool` or `completed`. Completing `{manager}-{id}` writes `{id}-completed.json`.

## Conventions

- Java 21. Do not introduce APIs newer than 21 unless you also bump `maven.compiler.release` and CI.
- Packages stay `beans`, `controller`, `dao`, `events`, `gui`, `services`, `util`. No reverse-DNS rename.
- JSON property names come from the current getters (`getID()` is `ID`, `getName()` is `name`). Do not rename accessors without a migration; there is no sample data in git, but real folders already on disk would break.
- Dates on screen are `dd/MM/yyyy` through `util.Dates`. Parsing is not lenient. Jackson still writes `java.util.Date` as epoch milliseconds. Leave that encoding alone.
- User ids were an 8-digit PeopleSoft convention. Do not require exactly 8 digits. Do reject blank ids and anything with whitespace or a path (`/`, `\`, `..`).
- Format with `./mvnw spotless:apply` before committing. `./mvnw verify` runs the format check.
- Tests are JUnit 5 and must stay single-threaded. They point `tasking.dataDir` at a temp folder. Do not construct `JFrame` in a unit test; Surefire sets `java.awt.headless=true`. `gui.ScreenshotHarness` is a main, not a test.
- Do not commit `data/`, `location.txt`, `User.json`, or backup folders. They can contain passwords.
- Do not add a license file unless someone asks.

## Commands

```bash
./mvnw test
./mvnw verify
./mvnw -B package
java -jar target/Tasking-0.0.1-SNAPSHOT.jar
java -jar target/Tasking-0.0.1-SNAPSHOT.jar New
java -jar target/Tasking-0.0.1-SNAPSHOT.jar Flush
```

Regenerate the README pictures on a machine with a display (or `xvfb-run`):

```bash
./mvnw -q test-compile dependency:build-classpath -DincludeScope=compile -Dmdep.outputFile=/tmp/cp.txt
java -cp "target/classes:target/test-classes:$(cat /tmp/cp.txt)" gui.ScreenshotHarness docs/screenshots
```

## Gotchas

- Passwords are compared and stored as plain text. `UserBean.toString` must not include the password.
- `getAllTasks` / `getAllUsers` return an empty list when the file is missing, and throw if the file exists but is not valid JSON. Do not treat a corrupt file as empty. The next save would wipe it.
- Creating a user who already exists must return without rewriting their task files.
- The pool file for a manager is created the first time someone reports to them. A second report must not overwrite that pool with `[]`.
- `isEight` is an 8-hour check, not an 8-digit-id check. It sums open estimates, manager-assigned estimates, and actual hours on completed tasks whose **estimated** date is today.
- Export walks the reporting tree. Keep the visited set. A cycle used to recurse forever.
- Backup must stay outside the data directory. The old `D:\` path was a bug, not a configuration users should restore.
- `Flush` keeps any filename containing `User`, `location`, or `tasknum`, and skips directories.
- Two writers on one shared folder can still clobber each other. There is no file lock. Do not "fix" that with a database unless the request is explicitly to replace the storage model.
- The board layout (`JTasking` GridBag, 1600×1000, a `JScrollPane` around each cell) is awkward. Leave it unless the task is specifically the layout. `addBadge` stays commented out; the png files were never in the repo.
- `NewTaskAddBean.getComponenets` is misspelled. Callers use that name. Do not rename it casually.

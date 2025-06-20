import {ConfigurationService} from 'src/app/core/services/configuration.service';
import {Configuration} from 'src/app/models/configuration.models';
import {Observable} from "rxjs";

// configuration
export function loadConfiguration(
    config: ConfigurationService
): () => Observable<Configuration> {
    return () => config.load();
}

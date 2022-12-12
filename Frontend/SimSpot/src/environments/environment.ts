// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  SimSpotUrl: "http://localhost:8080/api",

  // SimSpotUrl: "http://simspot-env.eba-sctpzcfa.us-east-1.elasticbeanstalk.com/api",
  stripePublishableKey:"pk_test_51M9PuKK30obX0VfCk1P3bBvHGJwNVplcFZz5LOORYmzr7kiVZqt8sAR3HWnvcqzbG0lwR4pDNR1CGDFPCgcdTeQT00voPHGYFx"
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.

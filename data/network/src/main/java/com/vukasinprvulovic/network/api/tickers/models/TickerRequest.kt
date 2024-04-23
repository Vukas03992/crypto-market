package com.vukasinprvulovic.network.api.tickers.models

import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs

// TODO implement proper mapping
fun TradingPairs.mapToHttpQuery(): String {
    return "symbols=tBTCUSD,tETHUSD,tCHSB:USD,tLTCUSD,tXRPUSD,tDSHUSD,tRRTUSD,tEOSUSD,tSANUSD,tDATUSD,tSNTUSD,tDOGE:USD,tLUNA:USD,tMATIC:USD,tNEXO:USD,tOCEAN:USD,tBEST:USD,tAAVE:USD,tPLUUSD,tFILUSD"
}
